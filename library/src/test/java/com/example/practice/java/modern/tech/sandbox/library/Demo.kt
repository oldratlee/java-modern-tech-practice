package com.example.practice.java.modern.tech.sandbox.library

import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking

fun main(args: Array<String>) = runBlocking<Unit> {
    println("1")
    delay(100)

    println("2")
//    val m2 = async {
//        delay(100)
//        "hello"
//    }
//    println(m2.await())

    delay(100_000)
}
