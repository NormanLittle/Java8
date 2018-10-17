package com.sandbox;

import javax.annotation.Nullable;
import java.util.List;

import com.google.common.base.Function;
import com.google.common.collect.Ordering;

import static java.util.stream.Collectors.joining;

import static com.google.common.collect.Lists.newArrayList;

public class OrderedListDemo {

    static final Function<String, Integer> noneIsLast = new Function<String, Integer>() {
        @Nullable
        @Override
        public Integer apply(@Nullable String action) {
            if ("NONE".equals(action)) {
                return 1;
            }
            return 0;
        }
    };

    public static void main(String[] args) {
        List<String> actions = newArrayList("NONE", "INSTALL", "UPGRADE", "REMOVE");

        System.out.println(actions.stream().collect(joining(",")));
        System.out.println(Ordering.natural()
                                   .onResultOf(noneIsLast)
                                   .sortedCopy(actions)
                                   .stream()
                                   .collect(joining(",")));
    }
}
