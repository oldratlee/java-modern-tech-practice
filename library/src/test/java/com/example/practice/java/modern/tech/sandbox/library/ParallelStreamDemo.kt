package com.example.practice.java.modern.tech.sandbox.library

import javax.management.Query.times
import java.util.stream.IntStream

fun main(args: Array<String>) {
    IntStream.range(0, 10).parallel().forEach {
        println("${Thread.currentThread().name}: $it")
    }

    println("======================")

    (0..10).toList().stream().parallel().forEach {
        println("${Thread.currentThread().name}: $it")
    }
}
