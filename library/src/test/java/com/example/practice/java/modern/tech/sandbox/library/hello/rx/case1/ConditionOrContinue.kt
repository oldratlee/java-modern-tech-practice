package com.example.practice.java.modern.tech.sandbox.library.hello.rx.case1

import io.reactivex.Flowable

fun main(args: Array<String>) {

    val ops: List<(BizPojo) -> Flowable<OpResult<BizPojo>>> = listOf(
            { bizData: BizPojo ->
                op1(bizData.id).map {
                    val success = it.data.length > 2 && Math.random() > 0.5
                    println("op1 success? $success")

                    OpResult(success, bizData.apply { if (success) d1 = it })
                }
            }
            ,
            { bizData: BizPojo ->
                op2(bizData.d1?.data).map {
                    val success = it.data.length > 2 && Math.random() > 0.5
                    println("op2 success? $success")

                    OpResult(success, bizData.apply { if (success) d2 = it })
                }
            }
            ,
            { bizData: BizPojo ->
                op3(bizData.d2?.data).map {
                    val success = it.data.length > 2 && Math.random() > 0.5
                    println("op3 success? $success")

                    OpResult(success, bizData.apply { if (success) d3 = it })
                }
            }
    )

    Flowable.just(BizPojo())
            .dependentSeqOps(ops)
            .subscribe { data -> println("received $data") }
}

//////////////////////////////////////////////////////

data class OpResult<T>(val success: Boolean, val data: T)

fun <T> Flowable<T>.dependentSeqOps(ops: List<(T) -> Flowable<OpResult<T>>>): Flowable<T> {

    fun Flowable<T>.go(step: Int = 0): Flowable<T> =
            if (step < ops.size) {
                flatMap { data: T ->
                    ops[step](data).flatMap {
                        val (success, d) = it
                        Flowable.just(d).run {
                            if (success) go(step + 1)
                            else this
                        }
                    }
                }
            } else this

    return go()
}

fun nestingStyle(flowable: Flowable<BizPojo>): Flowable<BizPojo> {
    return flowable.flatMap { bizPojo: BizPojo ->

        op1(bizPojo.id).flatMap { op1Data: Op1Data ->

            if (op1Data.data.length > 2) {
                Flowable.just(bizPojo)
            } else {
                op2(bizPojo.d1?.data).flatMap { op2Data: Op2Data ->

                    if (op2Data.data.length > 2) {
                        Flowable.just(bizPojo)
                    } else {
                        op3(bizPojo.d2?.data).flatMap { Flowable.just(bizPojo) }
                    }
                }
            }
        }
    }
}

//////////////////////////////////////////////////////

data class BizPojo(val id: Int = 0, var d1: Op1Data? = null, var d2: Op2Data? = null, var d3: Op3Data? = null)

////////////////////////////////////////
// Mock operation and data type
////////////////////////////////////////

data class Op1Data(val data: String)

data class Op2Data(val data: String)

data class Op3Data(val data: String)

/**
 * the mocked the heavy operation.
 */
fun op1(id: Int): Flowable<Op1Data> = Flowable.just(Op1Data("op1"))

fun op2(s: String?): Flowable<Op2Data> = Flowable.just(Op2Data("op2"))

fun op3(s: String?): Flowable<Op3Data> = Flowable.just(Op3Data("op3"))
