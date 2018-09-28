package com.sandbox;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;
import org.joda.time.LocalTime;

import static com.sandbox.TimeSlot.aTimeSlot;

class TimeSlot {

    private final LocalDateTime start;
    private final LocalDateTime end;

    private TimeSlot(LocalDateTime start, LocalDateTime end) {
        this.start = start;
        this.end = end;
    }

    static TimeSlot aTimeSlot(LocalDateTime start, LocalDateTime end) {
        return new TimeSlot(start, end);
    }

    LocalDate getDate() {
        return start.toLocalDate();
    }
}

public class DistinctDemo {

    private static final LocalDate today = LocalDate.now();

    public static void main(String[] args) {
        List<TimeSlot> timeSlots =
                Stream.of(1, 2, 4, 5, 5, 5, 6, 7, 7, 8, 8, 10, 14)
                      .map(today::plusDays)
                      .map(date -> aTimeSlot(aStartDateTimeFor(date), anEndDateTimeFor(date)))
                      .collect(Collectors.toList());

        System.out.println("Days returned: " + getNumberOfDistinctDaysIn(timeSlots));
    }

    private static long getNumberOfDistinctDaysIn(List<TimeSlot> timeSlots) {
        if (timeSlots == null || timeSlots.isEmpty()) {
            return 0;
        }
        return timeSlots.stream()
                        .map(TimeSlot::getDate)
                        .distinct()
                        .count();
    }

    private static LocalDateTime aStartDateTimeFor(LocalDate date) {
        return date.toLocalDateTime(new LocalTime(8, 0));
    }

    private static LocalDateTime anEndDateTimeFor(LocalDate date) {
        return date.toLocalDateTime(new LocalTime(17, 0));
    }
}
