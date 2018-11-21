package com.example.practice.java.modern.tech.sandbox.library.hello.rx;

import io.reactivex.Flowable;
import io.reactivex.flowables.GroupedFlowable;

import java.util.List;

public class GroupBy {
    public static void main(String[] args) {
        final List<GroupedFlowable<Integer, Integer>> groupedFlowables = Flowable.range(1, 20)
                .groupBy(x -> x % 10)
                .toList()
                .blockingGet();
        System.out.println(groupedFlowables);

        Flowable.range(1, 20000)
                .groupBy(x -> x % 10)
                .flatMapSingle( groupedFlowable -> groupedFlowable.toList())
                .toList()
                .blockingGet();
    }
}
