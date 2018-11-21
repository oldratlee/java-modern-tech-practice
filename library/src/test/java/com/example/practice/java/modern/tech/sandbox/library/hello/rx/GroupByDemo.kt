package com.example.practice.java.modern.tech.sandbox.library.hello.rx

import io.reactivex.Flowable
import io.reactivex.Single
import io.reactivex.flowables.GroupedFlowable
import io.reactivex.functions.Consumer

fun main(args: Array<String>) {
    Flowable.range(1, 2000)
            .groupBy { it % 10 }
            .flatMap { it.toList().toFlowable() }

    Flowable.range(1, 200)
            .groupBy { it % 10 }
            .toList()
            .blockingGet()

    val toList: Single<MutableList<GroupedFlowable<Int, Int>>> = Flowable.range(1, 200)
            .groupBy { it % 10 }
            .toList()
    toList.blockingGet()


    println("Hello")
    Thread.sleep(1000)
}
