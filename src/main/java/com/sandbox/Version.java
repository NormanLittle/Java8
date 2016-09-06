package com.sandbox;

import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

public class Version {
    private static final Function<String, Boolean> isAtLeastSeverPointFive = (version) -> {
        Matcher matcher = Pattern.compile("Version:\\s*(\\d+.\\d+).*").matcher(version);
        if (matcher.find()) {
            return Float.parseFloat(matcher.group(1)) >= 7.5;
        }
        return false;
    };

    public static void main(String[] args) {
        Stream.of("Version:8.0",
                  "Version:7.9",
                  "Version:7.8",
                  "Version:7.7",
                  "Version:7.6",
                  "Version:7.5",
                  "Version:7.5.1.1",
                  "Version: 7.5.1.0",
                  "Version:     7.0.1.6")
              .map(isAtLeastSeverPointFive)
              .forEach(System.out::println);
    }
}
