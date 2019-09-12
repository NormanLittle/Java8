package com.sandbox;

import java.util.Comparator;
import java.util.List;

import static java.util.Comparator.comparing;

import static com.google.common.collect.Lists.newArrayList;

public class CompareDemo {

    public static void main(String[] args) {
        List<Foo> source = newArrayList(new Foo(1, 2, 'a'),
                                        new Foo(1, 1, 'b'),
                                        new Foo(2, 1, 'b'),
                                        new Foo(2, 2, 'a'));
        System.out.println("Before:");
        source.stream()
              .map(Foo::toString)
              .forEach(System.out::println);

        System.out.println("\nAfter:");
        source.sort(Foo::compareTo);
        source.stream()
              .map(Foo::toString)
              .forEach(System.out::println);
    }

    static class Foo implements Comparable<Foo> {

        private final int priority;
        private final int number;
        private final char character;

        Foo(int priority, int number, char character) {
            this.priority = priority;
            this.number = number;
            this.character = character;
        }

        @Override
        public int compareTo(Foo o) {
            return comparing(Foo::getPriority)
                    .thenComparing(Foo::getPriorityOrder)
                    .compare(this, o);
        }

        @Override
        public String toString() {
            return String.format("Foo[priority=%d;number=%d;character=%s", priority, number, character);
        }

        int getPriority() {
            return priority;
        }

        int getNumber() {
            return number;
        }

        char getCharacter() {
            return character;
        }

        private int getPriorityOrder(Foo o) {
            if (priority == 1) {
                return Comparator.comparingInt(Foo::getNumber)
                                 .compare(this, o);
            }
            return Comparator.comparingInt(Foo::getCharacter)
                             .compare(this, o);
        }
    }
}
