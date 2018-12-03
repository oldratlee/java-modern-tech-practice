package com.example.practice.java.modern.tech.sandbox.library.hello.rx.event

import io.reactivex.Flowable
import io.reactivex.Scheduler
import io.reactivex.processors.PublishProcessor
import io.reactivex.schedulers.Schedulers
import io.reactivex.schedulers.TestScheduler
import org.junit.Test
import java.util.concurrent.TimeUnit
import java.util.concurrent.TimeUnit.MILLISECONDS
import java.util.concurrent.TimeUnit.SECONDS

/**
 * 用流式来 表达/实现 心跳 逻辑的实现：网络的数据包 中 补上 心跳包 的实现代码。
 *
 * 6行代码（操作符）打完，详见实现 [addPings]。
 *
 * 学习流的操作后，可以看到逻辑/流程的表达：
 *
 * - 非常 语义化 直白。
 * - 而且 有 完整的可测试性。
 * - 语义程序 非常 高。
 *
 * 更多说明参见：
 * [阻塞数据流的检测和测试 – RxJava](http://ifeve.com/%e9%98%bb%e5%a1%9e%e6%95%b0%e6%8d%ae%e6%b5%81%e7%9a%84%e6%a3%80%e6%b5%8b%e5%92%8c%e6%b5%8b%e8%af%95-rxjava/)
 */
class NetworkDataFlowAddPings_Test {

    fun <T> addPings(dataFlow: Flowable<T>, keepAlivePeriod: Long, keepAliveUnit: TimeUnit,
                     ping: T, pingPeriod: Long, pingPeriodUnit: TimeUnit,
                     scheduler: Scheduler): Flowable<T> {

        val pings = dataFlow
                .debounce(keepAlivePeriod, keepAliveUnit, scheduler)
                .flatMap { _: T ->
                    Flowable
                            .interval(0, pingPeriod, pingPeriodUnit, scheduler)
                            .map { _: Long -> ping }
                            .takeUntil(dataFlow)
                }
        return dataFlow.mergeWith(pings)
    }

    fun <T> addPings(events: Flowable<T>, keepAlivePeriod: Long, keepAliveUnit: TimeUnit,
                     ping: T, pingPeriod: Long, pingPeriodUnit: TimeUnit): Flowable<T> {
        return addPings(events, keepAlivePeriod, keepAliveUnit, ping, pingPeriod, pingPeriodUnit, Schedulers.computation())
    }

    @Test
    fun shouldAddPings() {
        val events = PublishProcessor.create<String>()

        val testScheduler = TestScheduler()
        val eventsWithPings = addPings(events, 1, SECONDS, "PING", 1, SECONDS, testScheduler)

        val test = eventsWithPings.test()
        events.onNext("A")
        test.assertValues("A")

        testScheduler.advanceTimeBy(999, MILLISECONDS)
        events.onNext("B")
        test.assertValues("A", "B")
        testScheduler.advanceTimeBy(999, MILLISECONDS)
        test.assertValues("A", "B")

        testScheduler.advanceTimeBy(1, MILLISECONDS)
        test.assertValues("A", "B", "PING")
        testScheduler.advanceTimeBy(999, MILLISECONDS)
        test.assertValues("A", "B", "PING")

        events.onNext("C")
        test.assertValues("A", "B", "PING", "C")

        testScheduler.advanceTimeBy(1000, MILLISECONDS)
        test.assertValues("A", "B", "PING", "C", "PING")
        testScheduler.advanceTimeBy(999, MILLISECONDS)
        test.assertValues("A", "B", "PING", "C", "PING")

        testScheduler.advanceTimeBy(1, MILLISECONDS)
        test.assertValues("A", "B", "PING", "C", "PING", "PING")
        testScheduler.advanceTimeBy(999, MILLISECONDS)
        test.assertValues("A", "B", "PING", "C", "PING", "PING")

        events.onNext("D")
        test.assertValues("A", "B", "PING", "C", "PING", "PING", "D")

        testScheduler.advanceTimeBy(999, MILLISECONDS)
        events.onNext("E")
        test.assertValues("A", "B", "PING", "C", "PING", "PING", "D", "E")
        testScheduler.advanceTimeBy(999, MILLISECONDS)
        test.assertValues("A", "B", "PING", "C", "PING", "PING", "D", "E")

        testScheduler.advanceTimeBy(1, MILLISECONDS)
        test.assertValues("A", "B", "PING", "C", "PING", "PING", "D", "E", "PING")

        testScheduler.advanceTimeBy(3000, MILLISECONDS)
        test.assertValues("A", "B", "PING", "C", "PING", "PING", "D", "E", "PING", "PING", "PING", "PING")
    }
}
