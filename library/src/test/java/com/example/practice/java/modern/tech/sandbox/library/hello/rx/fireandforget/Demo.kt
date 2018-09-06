package com.example.practice.java.modern.tech.sandbox.library.hello.rx.fireandforget

import io.reactivex.Flowable
import io.reactivex.schedulers.Schedulers
import java.util.*
import java.util.concurrent.TimeUnit

fun main(args: Array<String>) {
    Flowable.just("data")
            .map { i ->
                Flowable.just("$i in fire and forget, ${Date()}")
                        .subscribeOn(Schedulers.computation())
                        .delay(10, TimeUnit.MILLISECONDS)
                        .subscribe(::println)

                "$i in main"
            }.map { "$it, ${Date()}" }
            .subscribe(::println)

    Thread.sleep(1000)
}
