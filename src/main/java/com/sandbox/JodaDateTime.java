package com.sandbox;

import static com.google.common.base.CharMatcher.JAVA_DIGIT;

import java.util.function.Function;
import java.util.regex.Pattern;
import java.util.stream.Stream;
import org.joda.time.LocalTime;
import org.joda.time.format.DateTimeFormat;

public class JodaDateTime {
    static final Function<String, LocalTime> toLocalTime = s -> LocalTime.parse(s, DateTimeFormat.forPattern("HHmm"));

    public static void main(String[] args) {
        Stream.of("0800-1700", "0800-1300", "1200-1700", "1600-2000 DSO")
              .flatMap(Pattern.compile("-|\\s")::splitAsStream)
              .filter(JAVA_DIGIT::matchesAllOf)
              .map(toLocalTime)
              .forEach(System.out::println);
    }
}
