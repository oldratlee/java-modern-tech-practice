package com.example.practice.java.modern.tech.sandbox.library.hello.lambda;

import java.util.function.Function;

public class ImplementDataStructureByLambda {
    public static void main(String[] args) {
        final Function<Boolean, Integer> intPair = createPair(1, 2);
        System.out.printf("Pair: %s %s%n", first(intPair), second(intPair));

        final Function<Boolean, String> stringPair = createPair("hello", "world");
        System.out.printf("Pair: %s %s%n", first(stringPair), second(stringPair));

        final Function<Boolean, Object> pair = createPair(1, "hello");
        System.out.printf("Pair: %s %s%n", first(pair), second(pair));
    }

    private static <T> Function<Boolean, T> createPair(T first, T second) {
        return m -> {
            if (m) return first;
            else return second;
        };
    }

    private static <T> T first(Function<Boolean, T> pair) {
        return pair.apply(true);
    }


    private static <T> T second(Function<Boolean, T> pair) {
        return pair.apply(false);
    }
}
