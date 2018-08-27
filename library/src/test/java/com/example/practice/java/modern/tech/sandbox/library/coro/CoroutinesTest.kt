package com.example.practice.java.modern.tech.sandbox.coro

import kotlinx.coroutines.experimental.delay
import kotlinx.coroutines.experimental.runBlocking
import org.junit.Test
import kotlin.coroutines.experimental.buildSequence

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
        buildSequence {
            var i = 1
            while (i < 10) {
                yield(i)
                i += 1
            }
        }.forEach(::println)
    }
}
