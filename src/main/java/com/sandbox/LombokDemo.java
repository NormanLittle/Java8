package com.sandbox;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.*;

import java.io.IOException;

import static com.sandbox.LombokDemo.ImmutableFoo.anImmutableFoo;
import static lombok.AccessLevel.PRIVATE;

public class LombokDemo {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    private static <T> void testSerialization(T t, Class<T> clazz) throws IOException {
        System.out.println("Before: " + t);

        String json = objectMapper.writeValueAsString(t);
        System.out.println(json);

        T after = objectMapper.readValue(json, clazz);
        System.out.println("After: " + after);
    }

    public static void main(String[] args) throws IOException {
        ImmutableFoo immutableFoo = anImmutableFoo(1, "One");
        testSerialization(immutableFoo, ImmutableFoo.class);

        MutableFoo mutableFoo = new MutableFoo();
        mutableFoo.setId(2);
        mutableFoo.setName("Two");
        testSerialization(mutableFoo, MutableFoo.class);
    }

    @AllArgsConstructor(access = PRIVATE)
    @Getter
    @EqualsAndHashCode
    @ToString
    public static class ImmutableFoo {
        private final int id;
        private final String name;

        @JsonCreator
        public static ImmutableFoo anImmutableFoo(@JsonProperty("id") int id,
                                                  @JsonProperty("name") String name) {
            return new ImmutableFoo(id, name);
        }
    }

    @NoArgsConstructor
    @Getter
    @Setter
    @EqualsAndHashCode
    @ToString
    public static class MutableFoo {
        private int id;
        private String name;
    }
}
