package com.sandbox;

import java.util.stream.Stream;

public class EnumDemo {

    public static void main(String[] args) {
        Stream.of("1", "2", "3")
              .map(Foo::getInstance)
              .forEach(System.out::println);

        Stream.of("IN", "OUT", "ABOUT")
              .map(Foo::getInstanceFromName)
              .forEach(System.out::println);
    }

    enum Foo {
        IN("1"), OUT("2");

        private final String code;

        Foo(String code) {
            this.code = code;
        }

        public static Foo getInstance(String code) {
            return Stream.of(values())
                         .filter(v -> v.code.equals(code))
                         .findFirst()
                         .orElse(null);
        }

        public static Foo getInstanceFromName(String name) {
            return Stream.of(values())
                         .filter(v -> v.name().equals(name))
                         .findFirst()
                         .orElse(null);
        }
    }
}
