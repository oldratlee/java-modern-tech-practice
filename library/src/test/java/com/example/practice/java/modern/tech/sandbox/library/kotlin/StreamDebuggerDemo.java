package com.example.practice.java.modern.tech.sandbox.library.kotlin;

import java.util.ArrayList;
import java.util.List;

public class StreamDebuggerDemo {
    public static void main(String[] args) {
        List<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(2);
        list.add(5);
        list.add(8);
        list.add(14);

        list.stream()
                .filter(i -> i % 2 == 0)
                .map(it -> it + 100)
                .forEach(System.out::println);
    }
}
