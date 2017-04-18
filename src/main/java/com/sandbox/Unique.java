package com.sandbox;

import static com.google.common.collect.Sets.newHashSet;
import static java.lang.String.format;
import static java.util.UUID.randomUUID;
import static java.util.concurrent.Executors.newFixedThreadPool;
import static java.util.stream.Collectors.toList;

import java.util.List;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.stream.IntStream;
import com.google.common.base.Preconditions;
import com.google.common.base.Stopwatch;

class IdGenerator implements Callable<String> {
    private final int count;

    IdGenerator(int count) {
        this.count = count;
    }

    @Override
    public String call() throws Exception {
        String id = format("ROINLP%s",
                           Long.toString(Math.abs(randomUUID().getLeastSignificantBits()),
                                         Character.MAX_RADIX));

        System.out.printf("[%03d]:%s%n", count, id);
        return id;
    }
}

public class Unique {

    private static List<Future<String>> generateAll(ExecutorService executorService, List<IdGenerator> generators) throws InterruptedException {
        Stopwatch stopwatch = Stopwatch.createStarted();
        List<Future<String>> results = executorService.invokeAll(generators);
        System.out.println("Completed in " + stopwatch.stop());

        return results;
    }

    public static void main(String[] args) throws InterruptedException, ExecutionException {
        ExecutorService executorService = newFixedThreadPool(4);

        List<IdGenerator> generators =
                IntStream.rangeClosed(0, 100000)
                         .mapToObj(IdGenerator::new)
                         .collect(toList());

        List<Future<String>> generatorResults = generateAll(executorService, generators);
        executorService.shutdownNow();

        Set<String> uniqueIds = newHashSet();
        for (Future<String> future : generatorResults) {
            String id = future.get();

            boolean isUnique = uniqueIds.add(id);
            if (!isUnique) {
                System.err.printf("%s is not unique.%n", id);
            }
        }
    }
}
