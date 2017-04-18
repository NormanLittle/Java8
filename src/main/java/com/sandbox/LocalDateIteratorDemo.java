package com.sandbox;

import static com.google.common.collect.Lists.newArrayList;
import static java.util.Spliterator.ORDERED;
import static java.util.Spliterators.spliteratorUnknownSize;
import static org.joda.time.DateTimeConstants.MONDAY;
import static org.joda.time.DateTimeConstants.SATURDAY;
import static org.joda.time.DateTimeConstants.SUNDAY;

import java.util.Iterator;
import java.util.Set;
import java.util.stream.StreamSupport;
import org.joda.time.LocalDate;
import com.google.common.collect.ImmutableSet;

interface IterationConstraint<T> {
    boolean hasNext(T next);
    boolean isValid(T next);
    void next();
}

interface IterationFilter<T> {
    boolean isFiltered(T next);
    boolean isValid(T next);
}

abstract class AbstractIterationConstraint<T> implements IterationConstraint<T> {
    private IterationConstraint<T> orConstraint;

    AbstractIterationConstraint<T> or(IterationConstraint<T> orConstraint) {
        this.orConstraint = orConstraint;
        return this;
    }

    @Override
    public boolean hasNext(T next) {
        boolean hasNext = isValid(next);
        if (orConstraint != null) {
            hasNext &= orConstraint.hasNext(next);
        }
        return hasNext;
    }

    @Override
    public void next() {
        if (orConstraint != null) {
            orConstraint.next();
        }
    }
}

class FixedEndConstraint extends AbstractIterationConstraint<LocalDate> {
    private final LocalDate end;

    FixedEndConstraint(LocalDate end) {
        this.end = end;
    }

    @Override
    public boolean isValid(LocalDate next) {
        return next.isBefore(end) || next.isEqual(end);
    }
}

class MaximumIterationConstraint extends AbstractIterationConstraint<LocalDate> {
    private final int max;
    private int iteration;

    MaximumIterationConstraint(int max) {
        this.max = max;
        this.iteration = 0;
    }

    @Override
    public boolean isValid(LocalDate next) {
        boolean isValid = iteration < max;
        if (!isValid) {
            iteration = 0; // reset
        }
        return isValid;
    }

    @Override
    public void next() {
        super.next();
        iteration++;
    }
}

abstract class AbstractIterationFilter<T> implements IterationFilter<T> {
    IterationFilter<T> andFilter;

    AbstractIterationFilter<T> and(IterationFilter<T> andFilter) {
        this.andFilter = andFilter;
        return this;
    }

    @Override
    public boolean isFiltered(T next) {
        boolean isFiltered = !isValid(next);
        if (andFilter != null) {
            isFiltered |= andFilter.isFiltered(next);
        }
        return isFiltered;
    }
}

class WeekendDayFilter extends AbstractIterationFilter<LocalDate> {
    private static final Set<Integer> SATURDAY_AND_SUNDAY = ImmutableSet.of(SATURDAY, SUNDAY);

    @Override
    public boolean isValid(LocalDate next) {
        return !SATURDAY_AND_SUNDAY
                .contains(next.getDayOfWeek());
    }
}

class MondayFilter extends AbstractIterationFilter<LocalDate> {

    @Override
    public boolean isValid(LocalDate next) {
        return MONDAY != next.getDayOfWeek();
    }
}

class LocalDateIterator implements Iterator<LocalDate> {

    private LocalDate start;
    private LocalDate next;

    private IterationConstraint<LocalDate> iterationConstraint;
    private IterationFilter<LocalDate> iterationFilter;

    LocalDateIterator(LocalDate start) {
        this.start = this.next = start;
    }

    LocalDateIterator withIterationConstraint(IterationConstraint<LocalDate> iterationConstraint) {
        this.iterationConstraint = iterationConstraint;
        return this;
    }

    LocalDateIterator withIterationFilter(IterationFilter<LocalDate> iterationFilter) {
        this.iterationFilter = iterationFilter;
        return this;
    }

    @Override
    public boolean hasNext() {
        do {
            increment();
        } while (iterationFilter.isFiltered(next));

        boolean hasNext = iterationConstraint.hasNext(next);
        if (!hasNext) {
            next = start;
        }
        return hasNext;
    }

    @Override
    public LocalDate next() {
        iterationConstraint.next();
        return next;
    }

    private void increment() {
        next = next.plusDays(1);
    }
}

public class LocalDateIteratorDemo {

    public static void main(String[] args) {
        LocalDate start = LocalDate.now();
        LocalDate end = LocalDate.now().plusDays(10);

        LocalDateIterator localDateIterator =
                new LocalDateIterator(start)
                        .withIterationConstraint(new FixedEndConstraint(end)
                                                         .or(new MaximumIterationConstraint(5)))
                        .withIterationFilter(new WeekendDayFilter()
                                                     .and(new MondayFilter()));

        // Java 8 (convert iterator to stream)
        StreamSupport.stream(spliteratorUnknownSize(localDateIterator, ORDERED), false)
                     .forEach(System.out::println);

        // Guava (new ArrayList from iterator)
        newArrayList(localDateIterator)
                .forEach(System.out::println);
    }
}
