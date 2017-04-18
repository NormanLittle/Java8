package com.sandbox;

import static java.lang.String.format;
import static java.util.concurrent.Executors.newFixedThreadPool;
import static java.util.stream.Collectors.toList;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.stream.IntStream;
import org.joda.time.DateTime;

public class Unique {
    private static final int CIPHER[] = {1, 3, 3, 1, 8, 3, 5, 7, 2, 9, 0, 2, 8, 2, 9, 7, 7, 8, 1, 7};

    private static String generateId() {
        String now = String.valueOf(DateTime.now().getMillis());
        System.out.println(now);

        StringBuilder builder = new StringBuilder();
        for (int i = 0; i != now.length(); i++) {
            builder.append((now.charAt(i) + CIPHER[i]) % 10);
        }
        return format("ROINLP%s", builder.toString());
    }

    public static void main(String[] args) throws InterruptedException, ExecutionException {
        List<Callable<String>> tasks =
                IntStream.range(0, 500)
                         .mapToObj(i -> (Callable<String>) Unique::generateId)
                         .collect(toList());

        List<Future<String>> uniqueIds = newFixedThreadPool(500).invokeAll(tasks);
        for (Future<String> future : uniqueIds) {
            System.out.println(future.get());
        }
    }
}
