package com.example.practice.java.modern.tech.sandbox.library.kotlin

import java.util.*

fun main(args: Array<String>) {
    listOf(1, 2, 3, 5).asSequence()
            .filter { it % 2 == 0 }
            .map { it + 100 }
            .min()
            .let(::println)

    listOf(1, 2, 3, 5).asSequence()
            .filter { it % 2 == 0 }
            .map { it + 100 }
            .forEach(::println)

    listOf(1, 2, 3, 5, 100).stream()
            .filter { it % 2 == 0 }
            .map { it + 100 }
            .forEach(::println)


    val list = ArrayList<Int>()
    list.add(1)
    list.add(2)
    list.add(5)
    list.add(8)
    list.add(14)
    list.stream()
            .filter { i -> i!! % 2 == 0 }
            .map { it -> it!! + 100 }
            .forEach { println(it) }
}
