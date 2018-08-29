package com.example.practice.java.modern.tech.sandbox.library.hello.rx.case1

import io.reactivex.Flowable

fun main(args: Array<String>) {
    val shortcutCondition2NextStepProcess: List<Pair<(Double) -> Boolean, (Double) -> Flowable<Double>>> = listOf(
            { data: Double -> data > 0.5 } to
                    { data: Double -> complexComputation(data).apply { println("Round 1: $data") } }
            ,
            { data: Double -> data > 0.6 } to
                    { data: Double -> complexComputation(data).apply { println("Round 2: $data") } }
    )

    Flowable.just(Math.random())
            .shortcutOrNextStepProcess(shortcutCondition2NextStepProcess)
            .subscribe { data: Double -> println("received $data") }
}


fun Flowable<Double>.shortcutOrNextStepProcess(shortcutReturnCondition2NextStepProcess: List<Pair<(Double) -> Boolean, (Double) -> Flowable<Double>>>): Flowable<Double> {

    fun Flowable<Double>.go(step: Int = 0): Flowable<Double> = flatMap { data: Double ->
        if (step !in 0 until shortcutReturnCondition2NextStepProcess.size) { // no more process step, so only can short cut return
            Flowable.just(data)
        } else {
            val (condition, process) = shortcutReturnCondition2NextStepProcess[step]
            if (condition(data)) { // short cut condition satisfied :)
                Flowable.just(data)
            } else {
                process(data).go(step + 1)
            }
        }
    }

    return go()
}

/**
 * the mocked the heavy operation.
 */
fun complexComputation(data: Double): Flowable<Double> = Flowable.just(Math.random())
