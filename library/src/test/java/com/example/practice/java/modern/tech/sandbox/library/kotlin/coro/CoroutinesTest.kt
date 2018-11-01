package com.example.practice.java.modern.tech.sandbox.coro

import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import org.junit.Test

class CoroutinesTest {

    @Test
    fun coroutines() {
        runBlocking {
            delay(100)
            "Hello world"
        }.also {
            println(it)
        }
    }

    @Test
    fun test_buildSequence() {
        sequence {
            var i = 1
            while (i < 10) {
                yield(i)
                i += 1
            }
        }.forEach(::println)
    }
}
