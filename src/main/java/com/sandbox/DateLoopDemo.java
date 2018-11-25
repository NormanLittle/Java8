package com.sandbox;

import com.google.common.collect.Ordering;
import org.joda.time.LocalDate;

public class DateLoopDemo {

    public static void main(String[] args) {
        LocalDate start = LocalDate.now();
        LocalDate end = start.plusDays(90);

        System.out.printf("Start: %s; End: %s.%n", start, end);
        for (LocalDate searchStart = start, searchEnd = start.plusDays(30);
             searchStart.isBefore(end);
             searchStart = searchEnd.plusDays(1), searchEnd = Ordering.natural().min(end, searchStart.plusDays(30))) {
            System.out.printf("-> Searching %s to %s.%n", searchStart, searchEnd);
        }
    }
}
