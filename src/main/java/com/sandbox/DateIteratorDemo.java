package com.sandbox;

import javax.annotation.Nonnull;
import java.util.Iterator;
import java.util.NoSuchElementException;

import com.google.common.collect.Ordering;
import org.joda.time.LocalDate;

import static com.sandbox.DateRange.aDateRange;
import static com.sandbox.IterableDateRange.anIterableDateRange;

class DateRange {

    private final LocalDate start;
    private final LocalDate end;

    private DateRange(LocalDate start, LocalDate end) {
        this.start = start;
        this.end = end;
    }

    static DateRange aDateRange(LocalDate start, LocalDate end) {
        return new DateRange(start, end);
    }

    LocalDate getStart() {
        return start;
    }

    LocalDate getEnd() {
        return end;
    }
}

class IterableDateRange implements Iterable<DateRange> {

    private final LocalDate start;
    private final LocalDate end;
    private final int incrementSizeInDays;

    private IterableDateRange(LocalDate start, LocalDate end, int incrementSizeInDays) {
        this.start = start;
        this.end = end;
        this.incrementSizeInDays = incrementSizeInDays;
    }

    static IterableDateRange anIterableDateRange(LocalDate start, LocalDate end, int incrementSizeInDays) {
        return new IterableDateRange(start, end, incrementSizeInDays);
    }

    @Override
    @Nonnull
    public Iterator<DateRange> iterator() {
        return new DateRangeIterator(start, end, incrementSizeInDays);
    }

    private static class DateRangeIterator implements Iterator<DateRange> {

        private final int incrementSizeInDays;

        private LocalDate searchStart;
        private LocalDate searchEnd;
        private LocalDate end;

        private DateRangeIterator(LocalDate start, LocalDate end, int incrementSizeInDays) {
            this.incrementSizeInDays = incrementSizeInDays;

            this.searchStart = start;
            this.searchEnd = minimumOf(end, start.plusDays(incrementSizeInDays));
            this.end = end;
        }

        private static LocalDate minimumOf(LocalDate lhs, LocalDate rhs) {
            return Ordering.natural().min(lhs, rhs);
        }

        @Override
        public boolean hasNext() {
            return searchStart.isBefore(end);
        }

        @Override
        public DateRange next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            DateRange dateRange = aDateRange(searchStart, searchEnd);

            searchStart = searchEnd.plusDays(1);
            searchEnd = minimumOf(end, searchStart.plusDays(incrementSizeInDays));

            return dateRange;
        }
    }
}

public class DateIteratorDemo {

    public static void main(String[] args) {
        LocalDate start = LocalDate.now();
        LocalDate end = start.plusDays(90);

        System.out.printf("Start: %s; End: %s.%n", start, end);
        IterableDateRange searchDateRange = anIterableDateRange(start, end, 30);
        for (DateRange dateRange : searchDateRange) {
            System.out.printf("-> Searching %s to %s.%n", dateRange.getStart(), dateRange.getEnd());
        }
    }
}
