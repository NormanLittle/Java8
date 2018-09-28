package com.sandbox;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

import static java.lang.String.format;

public class PostCodeDemo {

    public static void main(String[] args) {
        Stream.of("eh88aa", "eh8 8aa", "fk104nl", "fk10 4nl", "EH223GZ", "Eh22 3Gz")
              .map(PostCode::parse)
              .forEach(System.out::println);
    }

    static class PostCode {

        private static final Pattern pattern = Pattern.compile("^([A-Za-z]{1,2})([0-9][\\w]?)\\s*([0-9])([A-Za-z]{2})$");

        private final String area;
        private final String district;
        private final String sector;
        private final String unit;

        private PostCode(String area, String district, String sector, String unit) {
            this.area = area;
            this.district = district;
            this.sector = sector;
            this.unit = unit;
        }

        static PostCode parse(String value) {
            Matcher matcher = pattern.matcher(value.toUpperCase().trim());
            if (!matcher.matches()) {
                throw new IllegalArgumentException("Invalid postcode format '" + value + "'");
            }
            return new PostCode(matcher.group(1), matcher.group(2), matcher.group(3), matcher.group(4));
        }

        @Override
        public String toString() {
            return format("PostCode: [area=%s, district=%s, sector=%s, unit=%s", area, district, sector, unit);
        }
    }
}
