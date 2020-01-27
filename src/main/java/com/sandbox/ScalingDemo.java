package com.sandbox;

import java.util.stream.Stream;

public class ScalingDemo {

    private static final int DURATION = 60;

    @SuppressWarnings("SameParameterValue")
    private static int scale(int duration, Integer factor) {
        if (factor == null) {
            return duration;
        }
        return duration + (factor * duration) / 100;
    }

    public static void main(String[] args) {
        Stream.of(null, -50, -25, -10, -5, -4, -3, -2, -1, 0, 1, 2, 3, 4, 5, 10, 25, 50, 100)
              .forEach(factor -> System.out.printf("Duration:%d; Factor:%d; Result:%d%n", DURATION, factor, scale(DURATION, factor)));
    }
}
