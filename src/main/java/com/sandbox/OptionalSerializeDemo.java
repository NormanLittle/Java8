package com.sandbox;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;

import java.util.Optional;
import java.util.stream.Stream;

import static com.sandbox.Foo.aFoo;
import static java.lang.String.format;

class Foo {
    private String name;
    private Optional<String> value;

    static Foo aFoo() {
        return new Foo();
    }

    public String getName() {
        return name;
    }

    public Foo withName(String name) {
        this.name = name;
        return this;
    }

    public Optional<String> getValue() {
        return value;
    }

    public Foo withValue(Optional<String> value) {
        this.value = value;
        return this;
    }

    public Foo withValue(String value) {
        return withValue(Optional.ofNullable(value));
    }

    @Override
    public String toString() {
        return format("Foo[name=%s;value=%s", name, value);
    }
}

public class OptionalSerializeDemo {

    private static ObjectMapper createObjectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        objectMapper.registerModule(new Jdk8Module());
        return objectMapper;
    }

    private static String serialize(Foo foo) {
        ObjectMapper objectMapper = createObjectMapper();
        try {
            return objectMapper.writeValueAsString(foo);
        } catch (JsonProcessingException e) {
            return null;
        }
    }

    public static void main(String[] args) {
        Stream.of(aFoo().withName("Foo").withValue("foo"),
                  aFoo().withName("Bar").withValue((String) null))
              .map(OptionalSerializeDemo::serialize)
              .forEach(System.out::println);
    }
}
