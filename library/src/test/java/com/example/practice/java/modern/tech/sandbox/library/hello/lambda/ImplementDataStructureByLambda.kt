package com.example.practice.java.modern.tech.sandbox.library.hello.lambda

fun main(args: Array<String>) {
    val intPair = createPair(1, 2)
    println("Pair: ${first(intPair)} ${second(intPair)}")

    val stringPair = createPair("hello", "world")
    println("Pair: ${first(stringPair)} ${second(stringPair)}")

    val pair = createPair(1, "hello")
    println("Pair: ${first(pair)} ${second(pair)}")
}

fun <T> createPair(first: T, second: T): Pair<T> = { selector: Boolean ->
    if (selector) first else second
}

fun <T> first(pair: Pair<T>) = pair(true)
fun <T> second(pair: Pair<T>) = pair(false)

typealias Pair<T> = (Boolean) -> T
