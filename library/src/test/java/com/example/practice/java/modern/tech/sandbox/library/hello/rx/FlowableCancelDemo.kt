package com.example.practice.java.modern.tech.sandbox.library.hello.rx

import io.reactivex.Flowable
import java.util.concurrent.ThreadLocalRandom
import java.util.concurrent.TimeUnit

fun main(args: Array<String>) {
    val disposable = Flowable.just(1, 2)
            .flatMap {
                Flowable.just(it)
                        .delay(it * 50L, TimeUnit.MILLISECONDS) // sleep 50, 100ms for item 1, 2
            }

            .doOnCancel { println("canceled") }

            .doOnError { println("error: $it") }
            .doOnComplete { println("complete") }
            .doOnTerminate { println("terminate") }

            .doFinally { println("finally") }
            .subscribe(::println)


    Thread.sleep(ThreadLocalRandom.current().nextLong(1, 3) * 80) // sleep 80 or 160ms randomly

    disposable.dispose()
}
