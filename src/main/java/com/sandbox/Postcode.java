package com.sandbox;

import static java.lang.String.format;

import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

public class Postcode {
    static final Function<String, String> toClickFormat = postcode -> {
        Matcher matcher = Pattern.compile("^([A-Za-z]{1,2}[0-9][\\w]?)\\s*([0-9][A-Za-z]{2})$")
                                 .matcher(postcode.trim());
        if (!matcher.matches()) {
            return postcode;
        }
        return format("%s %s", matcher.group(1), matcher.group(2));
    };

    public static void main(String[] args) {
        Stream.of("eh88aa", "fk104nl", "EH223GZ", "Eh22 3Gz")
              .map(toClickFormat)
              .forEach(System.out::println);
    }
}
