package org.example;

import org.openjdk.jmh.Main;
import org.openjdk.jmh.annotations.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class BenchmarkMain {
    public static void main(String[] args) throws IOException {
        System.out.println("qq");
        Main.main(args);
        System.out.println("qq1");
    }

    @Benchmark
    @Fork(value = 1, warmups = 2)
    @BenchmarkMode(Mode.AverageTime)
    public void sortBubble(ExecutionPlan executionPlan) {
        List<Integer> result = new ArrayList<>(executionPlan.integers);
        for (int i = 0; i < result.size(); i++) {
            for (int j = i; j < result.size(); j++) {
                if (result.get(i) > result.get(j)) {
                    Integer tempInteger = result.get(i);
                    result.set(i, result.get(j));
                    result.set(j, tempInteger);
                }
            }
        }
    }

    @Benchmark
    @Fork(value = 1, warmups = 2)
    @BenchmarkMode(Mode.AverageTime)
    public void sortDefault(ExecutionPlan executionPlan) {
        List<Integer> result = new ArrayList<>(executionPlan.integers);
        Collections.sort(result);
    }

    @State(Scope.Benchmark)
    public static class ExecutionPlan {
        @Param({ "100", "1000", "10000" })
        public int count;

        public List<Integer> integers;

        @Setup(Level.Invocation)
        public void setUp() {
            integers = IntStream.range(1, count)
                .map(it -> ThreadLocalRandom.current().nextInt(1, count))
                .boxed()
                .collect(Collectors.toList());
        }
    }
}
