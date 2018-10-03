package com.example.practice.java.modern.tech.sandbox.library.hello.rx

import io.reactivex.Flowable

fun main(args: Array<String>) {

    val flowableWithResource = Flowable
            .using(
                    {
                        println("new resource abc")
                        listOf("resource a", "resource b", "resource c")
                    },
                    { resource ->
                        println("create flowable with $resource")
                        Flowable.just(1, 2, 3)
                                .map { if (it == 3) throw RuntimeException("Error when $it") else it }
                    },
                    { resource -> println("dispose $resource") }
            )

    flowableWithResource
            .doOnError { println("error: it") }
            .subscribe(::println)
}
