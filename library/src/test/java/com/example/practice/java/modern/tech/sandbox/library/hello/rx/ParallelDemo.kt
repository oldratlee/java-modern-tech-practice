package com.example.practice.java.modern.tech.sandbox.library.hello.rx

import io.reactivex.Flowable
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.ThreadLocalRandom

fun main(args: Array<String>) {
    Flowable.range(0, 10)
            .concatMapEager  ({ ele: Int ->
                Flowable.just(ele)
                        .subscribeOn(Schedulers.io())
                        .doOnNext { sleep(1000, 1020) }
                        .doOnSubscribe { println("doOnSubscribe sub-flow of $ele") }
            }, 3, 128)
            .subscribe(::println)

    Thread.sleep(20_000)
}

private fun sleep(low: Long, upper: Long) {
    Thread.sleep(ThreadLocalRandom.current().nextLong(low, upper))
}
