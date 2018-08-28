package com.example.practice.java.modern.tech.sandbox.rx;

import io.reactivex.Flowable;

public class Main {
    public static void main(String[] args) {
        Flowable.just(1,2,3)
                .filter(integer -> true);


        Flowable.just(1, 2, 3)
                .filter(integer -> {
                    final boolean test = integer < 2;
                    if (!test) {
                        System.out.println(test);
                    }

                    return test;
                })
                .map(Main::add1)
                .onErrorReturn(throwable -> {
                    System.out.println(throwable);
                    return 42;
                })
                .subscribe(System.out::println);
    }

    static int add1(int x) {
        return x + 1;
    }
}
