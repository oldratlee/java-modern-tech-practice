package com.example.practice.java.modern.tech.sandbox.coro

import kotlinx.coroutines.*
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


    @Test
    fun test1() {
        runBlocking {
            val hello: Deferred<String> = async {
                delay(100)
                println("hello: [${Thread.currentThread().name}]")
                hello()
            }
//            println("${Date()}: 2")
//            val world: Deferred<String> = async {
//                println("world: ${Thread.currentThread().name}")
//                world()
//            }

            println("runBlocking [${Thread.currentThread().name}] 1")

            println(hello.await().length)
            println("runBlocking [${Thread.currentThread().name}] 2")

//            println("${Date()}: 4")
//            println(world.await().length)

            "Hello"
        }

    }

    suspend fun hello(): String {
        delay(1000)
        return "hello"
    }

    suspend fun world(): String {
        delay(1000)
        return "world"
    }

    @Test
    fun eee() {
        runBlocking<Unit> {
            launch {
                // context of the parent, main runBlocking coroutine
                println("main runBlocking      : I'm working in thread ${Thread.currentThread().name}")
            }
            launch(Dispatchers.Unconfined) {
                // not confined -- will work with main thread
                println("Unconfined            : I'm working in thread ${Thread.currentThread().name}")
            }
            launch(Dispatchers.Default) {
                // will get dispatched to DefaultDispatcher
                println("Default               : I'm working in thread ${Thread.currentThread().name}")
            }
            launch(newSingleThreadContext("MyOwnThread")) {
                // will get its own new thread
                println("newSingleThreadContext: I'm working in thread ${Thread.currentThread().name}")
            }
        }
    }
}

fun main() = runBlocking<Unit> {
    launch {
        // context of the parent, main runBlocking coroutine
        println("main runBlocking      : I'm working in thread ${Thread.currentThread().name}")
    }
    launch(Dispatchers.Unconfined) {
        // not confined -- will work with main thread
        println("Unconfined            : I'm working in thread ${Thread.currentThread().name}")
    }
    launch(Dispatchers.Default) {
        // will get dispatched to DefaultDispatcher
        println("Default               : I'm working in thread ${Thread.currentThread().name}")
    }
    launch(newSingleThreadContext("MyOwnThread")) {
        // will get its own new thread
        println("newSingleThreadContext: I'm working in thread ${Thread.currentThread().name}")
    }
}
