package com.example.practice.java.modern.tech.sandbox.library.hello.rx

import io.reactivex.Flowable
import io.reactivex.functions.BiFunction
import java.util.concurrent.TimeUnit
import kotlin.concurrent.thread

fun main(args: Array<String>) {
    println("Warm-up round!")
    val start1 = System.nanoTime()
    val flowable = Flowable.zip(
            Flowable.just("s1", "s2").delay(400, TimeUnit.MILLISECONDS),
            Flowable.just(1, 2, 3).delay(500, TimeUnit.MILLISECONDS),
            BiFunction { s: String, i: Int -> s + i }
    )

    flowable.blockingSubscribe {
        println("subscribe ${getTimeCostString(start1)}: $it")
    }

    println("Actual test.")
    val start2 = System.nanoTime()
    flowable.subscribe {
        println("subscribe ${getTimeCostString(start2)}: $it")
    }

    Thread.sleep(1_000)

    println("Thread create test.")
    val start3 = System.nanoTime()
    thread {
        println("start thread ${getTimeCostString(start3)}")
    }

    Thread.sleep(1_000)
}

private fun getTimeCostString(startNano: Long) = String.format("%,d micro seconds", TimeUnit.NANOSECONDS.toMicros((System.nanoTime() - startNano)))

