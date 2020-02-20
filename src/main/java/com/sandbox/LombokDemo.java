package com.sandbox;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.io.IOException;

public class LombokDemo {

    public static void main(String[] args) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();

        Order before = new Order("VR123456789", 1);
        System.out.println("Before: " + before);

        String json = objectMapper.writeValueAsString(before);
        System.out.println(json);

        Order after = objectMapper.readValue(json, Order.class);
        System.out.println("After: " + after);
    }

    @Getter
    @EqualsAndHashCode
    @ToString
    private static class Order {
        private final String id;
        private final int quantity;

        @JsonCreator
        private Order(@JsonProperty("id") String id,
                      @JsonProperty("quantity") int quantity) {
            this.id = id;
            this.quantity = quantity;
        }
    }
}
