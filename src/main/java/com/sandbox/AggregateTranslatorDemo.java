package com.sandbox;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collector;
import java.util.stream.Stream;

import static java.util.Objects.hash;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toList;

class Resource {
    private final String type;
    private final String name;
    private final String code;
    private final String serial;

    Resource(String type, String name, String code, String serial) {
        this.type = type;
        this.name = name;
        this.code = code;
        this.serial = serial;
    }

    String getType() {
        return type;
    }

    String getName() {
        return name;
    }

    String getCode() {
        return code;
    }

    String getSerial() {
        return serial;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Resource resource = (Resource) o;
        return Objects.equals(type, resource.type) &&
               Objects.equals(name, resource.name) &&
               Objects.equals(code, resource.code) &&
               Objects.equals(serial, resource.serial);
    }

    @Override
    public int hashCode() {
        return hash(type, name, code, serial);
    }
}

class Inventory {
    private final String type;
    private final String name;
    private final int quantity;

    Inventory(String type, String name, int quantity) {
        this.type = type;
        this.name = name;
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "Inventory{" +
               "type='" + type + '\'' +
               ", name='" + name + '\'' +
               ", quantity=" + quantity +
               '}';
    }
}

abstract class AggregateTranslator<FROM, TO, KEY> {
    private final Collector<FROM, ?, Map<KEY, List<FROM>>> groupingBy;

    AggregateTranslator(Function<FROM, KEY> classifier) {
        this.groupingBy = groupingBy(classifier);
    }

    List<TO> translate(List<FROM> from) {
        Map<KEY, List<FROM>> groups = from.stream().collect(groupingBy);
        return groups.values()
                     .stream()
                     .map(this::aggregate)
                     .collect(toList());
    }

    abstract TO aggregate(List<FROM> group);
}

class ResourceAggregateTranslator extends AggregateTranslator<Resource, Inventory, Integer> {

    ResourceAggregateTranslator() {
        super(resource -> hash(resource.getType()) + hash(resource.getName()) + hash(resource.getCode()) + hash(resource.getSerial()));
    }

    @Override
    Inventory aggregate(List<Resource> resources) {
        Resource resource = resources.get(0);
        return new Inventory(resource.getType(), resource.getName(), resources.size());
    }
}

public class AggregateTranslatorDemo {

    public static void main(String[] args) {
        ResourceAggregateTranslator translator = new ResourceAggregateTranslator();
        List<Resource> resources = Stream.of(new Resource("STB", "Sky Q 1TB", null, null),
                                             new Resource("STB", "Sky Q 2TB", null, null),
                                             new Resource("STB", "Sky Q 1TB", null, null),
                                             new Resource("BBR", "Sky Q Hub", "12345", "987654321"))
                                         .collect(toList());

        List<Inventory> inventory = translator.translate(resources);
        inventory.forEach(System.out::println);
    }
}
