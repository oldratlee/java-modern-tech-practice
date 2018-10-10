package com.example.practice.java.modern.tech.sandbox.library.hello.rx

import io.reactivex.Flowable
import java.util.*
import java.util.concurrent.Callable


fun main(args: Array<String>) {
    val ids = Flowable
            .fromCallable<UUID> { UUID.randomUUID() }
            .repeat()
            .take(100)

    ids.subscribe { id -> slowLoadBy(id) }
}


fun slowLoadBy(id: UUID): String {
    Thread.sleep(100)
    return id.toString()
}
