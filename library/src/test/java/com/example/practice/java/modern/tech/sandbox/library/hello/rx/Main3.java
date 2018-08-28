package com.example.practice.java.modern.tech.sandbox.library.hello.rx;

import io.reactivex.Flowable;
import io.reactivex.schedulers.Schedulers;

public class Main3 {
    public static void main(String[] args) throws Exception {
        Flowable.range(1, 5)
                .flatMap(
                        x -> {
                            Flowable<Integer> flowable = Flowable.just(x + 100)
                                    .doOnNext(integer -> {
                                        System.out.println(x + ": doOnNext");
                                        final int sleep = 120 - x * 20;
                                        Thread.sleep(sleep);
                                        System.out.println(x + ": doOnNext, finished sleep " + sleep);
                                    })
//                                    .doOnSubscribe(subscription -> System.out.println(x + ": doOnSubscribe"))
                                    .doFinally(() -> System.out.println(x + ": doFinally"))
                                    .doOnTerminate(() -> System.out.println(x + ": doOnTerminate"))
                                    .doAfterTerminate(() -> System.out.println(x + ": doAfterTerminate"));
                            if (2 == x) flowable = flowable.subscribeOn(Schedulers.io());
                            return flowable;
                        }
                        , 1
                )
                .doOnNext(integer -> System.out.println(integer + ": doOnNext in main"))
                .doFinally(() -> System.out.println("doFinally"))
                .doOnTerminate(() -> System.out.println("doOnTerminate"))
                .doAfterTerminate(() -> System.out.println("doAfterTerminate"))
                .subscribe(System.out::println);

        Thread.sleep(1000);
    }

}
