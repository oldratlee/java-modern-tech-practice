package com.example.practice.java.modern.tech.sandbox

import kotlinx.coroutines.experimental.delay
import kotlinx.coroutines.experimental.runBlocking
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
}
