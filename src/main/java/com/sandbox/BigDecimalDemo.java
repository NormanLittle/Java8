package com.sandbox;

import java.math.BigDecimal;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.math.BigDecimal.ROUND_HALF_EVEN;

public class BigDecimalDemo {

    private static Function<String, BigDecimal> toBigDecimal = s ->
            new BigDecimal(s)
                    .divide(new BigDecimal("1000000"), 6, ROUND_HALF_EVEN);

    public static void main(String[] args) {
        List<BigDecimal> values =
                Stream.of("55876130", "-3517040", "0")
                      .map(toBigDecimal)
                      .collect(Collectors.toList());

        values.forEach(System.out::println);
    }
}
