package com.example.practice.java.modern.tech.sandbox.rx;

import io.reactivex.Flowable;


public class Main2 {
    public static void main(String[] args)throws Exception {
        final Flowable<Integer> source = Flowable.just(1, 2, 3)
                .doOnNext(integer -> System.out.println("---> " + integer))
                .publish()
                .autoConnect(3);

        final Flowable<Integer> m1 = source.map(x -> x + 100);
        final Flowable<Integer> m2 = source.map(x -> x + 2000);

        Flowable.zip(source, m1, m2, (integer, integer2, integer3) -> integer + " " + integer2 + " " + integer3)
                .subscribe(System.out::println);


       Thread.sleep(10000);
    }

    static int add1(int x) {
        return x + 1;
    }
}
