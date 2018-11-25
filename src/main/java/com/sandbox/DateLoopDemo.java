package com.sandbox;

import com.google.common.collect.Ordering;
import org.joda.time.LocalDate;

public class DateLoopDemo {

    private static LocalDate minimumOf(LocalDate lhs, LocalDate rhs) {
        return Ordering.natural().min(lhs, rhs);
    }

    public static void main(String[] args) {
        LocalDate start = LocalDate.now();
        LocalDate end = start.plusDays(90);

        System.out.printf("Start: %s; End: %s.%n", start, end);
        for (LocalDate searchStart = start, searchEnd = minimumOf(end, start.plusDays(30));
             searchStart.isBefore(end);
             searchStart = searchEnd.plusDays(1), searchEnd = minimumOf(end, searchStart.plusDays(30))) {
            System.out.printf("-> Searching %s to %s.%n", searchStart, searchEnd);
        }
    }
}
