package com.example.practice.java.modern.tech.sandbox.library.hello.rx

import io.reactivex.Flowable
import io.reactivex.rxkotlin.zipWith
import java.util.concurrent.ThreadLocalRandom

fun main(args: Array<String>) {
    // 无穷的随机int的流
    val randomIntFlow = Flowable.fromCallable { ThreadLocalRandom.current().nextInt(0, 1000) }.repeat()

    randomIntFlow.take(2).subscribe(::println)
    println("===================================")
    randomIntFlow.take(2).subscribe(::println)
    println("===================================")

    // 1的流
    val flowOf1 = randomIntFlow.take(1_000_000) // 1_000_000个随机int的流
            .publish().autoConnect(2) // 转成热流： 避免上面Flow多次生成，后面的 fx/gx 用的是 同一个随机流

    val fxFlow = flowOf1.map { it + 1 }
    val gxFlow = flowOf1.map { it - 1 }

    val zippedFlow = fxFlow.zipWith(gxFlow) { x, y -> x - y }

    zippedFlow
            .distinct() // 去重
            .blockingSubscribe { println(it) } // 打印
}
