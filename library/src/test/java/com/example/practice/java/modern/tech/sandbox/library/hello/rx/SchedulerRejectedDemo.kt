package com.example.practice.java.modern.tech.sandbox.library.hello.rx

import io.reactivex.Flowable
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.SynchronousQueue
import java.util.concurrent.ThreadFactory
import java.util.concurrent.ThreadPoolExecutor
import java.util.concurrent.TimeUnit

fun main(args: Array<String>) {
    val scheduler = ThreadPoolExecutor(1, 1, 60, TimeUnit.SECONDS,
            SynchronousQueue(), ThreadFactory { r -> Thread(r, "my-thread").apply { isDaemon = true } }, ThreadPoolExecutor.AbortPolicy()
    ).let {
        it.submit { }.get()
        Flowable.just(1, 2, 3).subscribe { }

        // it.submit { Thread.sleep(10_000) }
        Schedulers.from(it)
    }

    Flowable.just(1, 2, 3)
            .doOnNext {
                println("${Thread.currentThread().name} doOnNext 1: $it")
            }
            .doOnError {
                println("${Thread.currentThread().name} doOnError 1: $it")
            }
            .doFinally {
                println("${Thread.currentThread().name} doFinally 1")
            }

            .observeOn(scheduler)

            .flatMap { item: Int ->
                Flowable.just(item)
                        .delay(10, TimeUnit.MILLISECONDS, scheduler)
                        .doOnNext {
                            println("sleep $it")
                            Thread.sleep(1000L)
                            println("sleep finished $it")
                        }
            }
            .doOnError {
                println("${Thread.currentThread().name} doOnError 2: $it")
            }
            .doFinally {
                println("${Thread.currentThread().name} doFinally 2")
            }
            .subscribe {
                println("${Thread.currentThread().name} subscribe 2: $it")
            }


    Thread.sleep(4_000)
}
