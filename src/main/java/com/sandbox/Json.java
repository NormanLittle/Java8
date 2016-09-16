package com.sandbox;

import static java.lang.String.format;

import java.io.IOException;
import java.util.List;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Optional;

public class Json {
    private static final ObjectMapper mapper = new ObjectMapper();
    private static final String JSON_LIST = "[\n" +
                                            "    {\n" +
                                            "        \"name\": \"engineerCache\",\n" +
                                            "        \"type\": \"ehCache\"\n" +
                                            "    },\n" +
                                            "    {\n" +
                                            "        \"name\": \"partnerIdForEngineer\",\n" +
                                            "        \"type\": \"ehCache\"\n" +
                                            "    }\n" +
                                            "]";

    public static void main(String[] args) throws IOException {
        Optional<List<Foo>> foos = Optional.fromNullable(mapper.readValue(JSON_LIST,
                                                                          mapper.getTypeFactory().constructCollectionType(List.class, Foo.class)));
        if (foos.isPresent()) {
            foos.get().stream().forEach(System.out::println);
        }
    }

    static class Foo {
        private String name = null;
        private String type = null;

        public String getName() {
            return name;
        }

        public String getType() {
            return type;
        }

        @Override
        public String toString() {
            return format("Name=%s;Type=%s", getName(), getType());
        }
    }
}
