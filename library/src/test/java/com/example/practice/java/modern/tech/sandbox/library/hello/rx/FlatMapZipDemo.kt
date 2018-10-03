package com.example.practice.java.modern.tech.sandbox.library.hello.rx

import io.reactivex.Flowable
import io.reactivex.rxkotlin.zipWith
import java.util.concurrent.ThreadLocalRandom
import java.util.concurrent.TimeUnit

fun main(args: Array<String>) {
    val just = Flowable.just(1, 2, 3).publish().autoConnect(2)

    val fm = just.flatMap {
        Flowable.just(it)
                .delay(ThreadLocalRandom.current().nextLong(100), TimeUnit.MILLISECONDS)
    }

    just.zipWith(fm)
            .subscribe(::println) // not paired sequentially

    Thread.sleep(1000)
}
