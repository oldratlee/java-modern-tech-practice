package com.example.practice.java.modern.tech.sandbox.library.hello.rx

import io.reactivex.Flowable
import io.reactivex.functions.Function
import java.util.*
import java.util.concurrent.ThreadLocalRandom
import java.util.concurrent.TimeUnit

fun main(args: Array<String>) {
    // flat map, warm-up round
    testMap("flatmap(warm-up round)", true) { flatMap(it) }

    println()
    println("-------------------- delay --------------------")
    println()

    testMap("flatmap", true) { flatMap(it) }
    testMap("concatMap", true) { concatMap(it) }
    testMap("concatMapEager", true) { concatMapEager(it) }


    println()
    println("-------------------- no delay --------------------")
    println()

    testMap("flatmap", false) { flatMap(it) }
    testMap("concatMap", false) { concatMap(it) }
    testMap("concatMapEager", false) { concatMapEager(it) }
}

typealias FlowMapper = Function<Int, Flowable<Int>>

fun testMap(tag: String, delay: Boolean, flowMerge: Flowable<Int>.(FlowMapper) -> Flowable<Int>) {

    val delayMsg = if (delay) "with delay" else ""
    println("[${time()}] test $tag $delayMsg\n--------------")

    Flowable.range(0, 10)

            .flowMerge(Function { item: Int ->
                Flowable.just(item)
                        .run<Flowable<Int>, Flowable<Int>> {
                            val span = ThreadLocalRandom.current().nextLong(100, 110)
                            if (delay) delay(span, TimeUnit.MILLISECONDS)
                            else doOnNext { Thread.sleep(span) }
                        }
            })

            .doOnSubscribe { println("[${time()}] $tag doOnSubscribe") }
            .doOnNext { println("[${time()}] doOnNext [${Thread.currentThread().name}]: $it") }
            .blockingSubscribe { println("[${time()}] $tag subscribe: $it") }

    println()
}


/*
http://www.java2s.com/Tutorials/Java/Java_Format/0120__Java_Format_Dates_Times.htm

Suffix	Meaning
H	Format time as two-digit hour of the day for the 24-hour clock. The valid values are 00 to 23. 00 is used for midnight.
I	Format a two-digit hour of the day for the 12-hour clock. The valid values are 01 to 12.
k	Format time the same as the H suffix except that it does not add a leading zero to the output. Valid values are 0 to 23.
l	Format time the same as 'I' suffix except that it does not add a leading zero. Valid values are 1 to 12.

M	A two-digit minute within an hour. Valid values are 00 to 59.

S	A two-digit second. Valid values are 00 to 60.

L	A three-digit millisecond. Valid values are 000 to 999.

N	A nine-digit nanosecond. The valid values are 000000000 to 999999999.

p	Format a locale-specific morning or afternoon string in lowercase. For US locale, "am" or "pm". To get "AM" and "PM", use the uppercase variant 'T' as the conversion character.
z	Output the numeric time zone offset from GMT (e.g., +0530).
Z	Output a string abbreviation of the time zone (e.g., CST, EST, IST, etc).

s	Output seconds since January 1, 1970 midnight UTC.
Q	Output milliseconds since January 1, 1970 midnight UTC.
 */
fun time(): String = String.format("%tH:%<tM:%<tS.%<tL", Date())
