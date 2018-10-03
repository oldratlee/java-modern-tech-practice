package com.example.practice.java.modern.tech.sandbox.library.hello.rx

import io.reactivex.Flowable

fun main(args: Array<String>) {
    val cache = Flowable
            .fromCallable { System.nanoTime() % 1000 }
            .repeat()
            .take(5)
            .cache()


    cache.subscribe(::println)

    println()

    cache.subscribe(::println)
}
