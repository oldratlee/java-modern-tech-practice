package com.example.practice.java.modern.tech.sandbox.rx.either

import io.reactivex.Flowable
import io.reactivex.rxkotlin.toFlowable
import io.vavr.control.Either

const val WRONG_DATA = "Wrong Data"

fun main(args: Array<String>) {

    listOf("hello", WRONG_DATA, "world").toFlowable()
            .toRightFlowable<String, String>()
            .startWith("Error!".toLeft<String, String>())
            .mapRight { right ->
                if (right == WRONG_DATA) "$right -> turn to Left error".toLeft<String, String>()
                else right.toRight()
            }
            .flatMapRight { right ->
                "$right -> processed right data"
                        .toRight<String, String>().to1EleFlowable()
            }
            .flatMapRight { right ->
                "$right -> all right data turn to left error"
                        .toLeft<String, String>().to1EleFlowable()
            }
            .flatMapRight { right ->
                "$right -> NEVER meet this op, because above op"
                        .toRight<String, String>().to1EleFlowable()
            }
            .subscribe(::println)

}

fun <L, R : Any> Flowable<R>.toRightFlowable(): Flowable<Either<L, R>> = map { it.toRight<L, R>() }
fun <T : Any> T.to1EleFlowable(): Flowable<T> = Flowable.just(this)

inline fun <L, R, R2> Flowable<Either<L, R>>.mapRight(crossinline mapper: (right: R) -> Either<L, R2>)
        : Flowable<Either<L, R2>> = map { either ->
    either.fold(
            { left -> left.toLeft<L, R2>() },
            { right -> mapper(right) }
    )
}

inline fun <L, R, R2> Flowable<Either<L, R>>.flatMapRight(crossinline mapper: (right: R) -> Flowable<Either<L, R2>>)
        : Flowable<Either<L, R2>> = flatMap { either ->
    either.fold(
            { left -> left.toLeft<L, R2>().to1EleFlowable() },
            { right -> mapper(right) }
    )
}

inline fun <L, R, T : Any> Flowable<Either<L, R>>.foldEither(
        crossinline leftMapper: (left: L) -> T,
        crossinline rightMapper: (right: R) -> T
): Flowable<T> = map { either ->
    either.fold({ leftMapper(it) }, { rightMapper(it) })
}


fun <L, R> L.toLeft(): Either<L, R> = Either.left(this)
fun <L, R> R.toRight(): Either<L, R> = Either.right(this)
