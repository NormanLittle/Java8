package com.sandbox;

import static java.lang.String.format;
import static java.util.concurrent.TimeUnit.SECONDS;

import java.util.function.IntFunction;
import java.util.stream.IntStream;
import com.google.common.base.Stopwatch;

public class StopWatchDemo {
    private static IntFunction<String> timeOperation = duration -> {
        Stopwatch stopwatch = Stopwatch.createStarted();
        try {
            Thread.sleep(duration * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return format("Duration was %d second(s).", stopwatch.stop().elapsed(SECONDS));
    };

    public static void main(String[] args) {
        IntStream.rangeClosed(0, 3)
                 .mapToObj(timeOperation)
                 .forEach(System.out::println);
    }
}
