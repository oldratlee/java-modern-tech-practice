package com.example.practice.java.modern.tech.sandbox.library.hello.rx

import io.reactivex.Flowable
import org.reactivestreams.Subscriber

fun main(args: Array<String>) {
    Flowable.empty<String>() // empty stream
            .defaultIfEmpty("a") // set a default data
            .doOnNext { println(it) }

            .filter { false } // empty stream
            .switchIfEmpty(
                    // set a default data stream
                    Flowable.just("b", "b")
                            .doOnSubscribe { println("log: found empty stream!") }
            )
            .doOnNext { println(it) }

            .filter { false } // empty stream
            // self implementation publisher, DO NOT use this solution in biz code!!
            .switchIfEmpty { subscriber: Subscriber<in String> ->
                subscriber.onNext("c")
                subscriber.onNext("c")
                subscriber.onComplete()
            }

            .subscribe(::println)
}
