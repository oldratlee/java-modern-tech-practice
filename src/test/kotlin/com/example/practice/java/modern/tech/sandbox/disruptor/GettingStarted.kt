/**
 * @see <a href="https://github.com/LMAX-Exchange/disruptor/wiki/Getting-Started">Getting the Disruptor - official doc</a>
 * @see <a href="https://blog.csdn.net/wh0426/article/details/45748637">disruptor使用示例</a>
 */
package com.example.practice.java.modern.tech.sandbox.disruptor

import com.lmax.disruptor.EventFactory
import com.lmax.disruptor.EventHandler
import com.lmax.disruptor.EventTranslatorOneArg
import com.lmax.disruptor.RingBuffer
import com.lmax.disruptor.dsl.Disruptor
import java.nio.ByteBuffer
import java.util.concurrent.Executors


data class LongEvent(var value: Long = 0)


class LongEventFactory : EventFactory<LongEvent> {
    override fun newInstance(): LongEvent = LongEvent()
}


class LongEventHandler : EventHandler<LongEvent> {
    override fun onEvent(event: LongEvent, sequence: Long, endOfBatch: Boolean) =
            println("Event: $event, sequence: $sequence, endOfBatch: $endOfBatch")
}


class LongEventProducer(private val ringBuffer: RingBuffer<LongEvent>) {
    fun onData(bb: ByteBuffer) {
        val sequence = ringBuffer.next()  // Grab the next sequence
        try {
            val event = ringBuffer.get(sequence) // Get the entry in the Disruptor
            // for the sequence
            event.value = bb.getLong(0)  // Fill with data
        } finally {
            ringBuffer.publish(sequence)
        }
    }
}


class LongEventProducerWithTranslator(private val ringBuffer: RingBuffer<LongEvent>) {
    fun onData(bb: ByteBuffer): Unit = ringBuffer.publishEvent(TRANSLATOR, bb)

    companion object {
        private val TRANSLATOR = EventTranslatorOneArg<LongEvent, ByteBuffer> { event: LongEvent, _: Long, bb: ByteBuffer ->
            event.value = bb.getLong(0)
        }
    }
}


fun main(args: Array<String>) {
    // Executor that will be used to construct new threads for consumers
    val executor = Executors.newCachedThreadPool()
    // The factory for the event
    val factory = LongEventFactory()
    // Specify the size of the ring buffer, must be power of 2.
    val bufferSize = 1024
    // Construct the Disruptor
    @Suppress("DEPRECATION")
    val disruptor = Disruptor(factory, bufferSize, executor)

    // Connect the handler
    disruptor.handleEventsWith(LongEventHandler())

    // Start the Disruptor, starts all threads running
    disruptor.start()

    // Get the ring buffer from the Disruptor to be used for publishing.
    val ringBuffer = disruptor.ringBuffer

    val producer = LongEventProducer(ringBuffer)
    val producerWithTranslator = LongEventProducerWithTranslator(ringBuffer)

    val bb = ByteBuffer.allocate(8)
    for (l in 0L..Long.MAX_VALUE) {
        bb.putLong(0, l)
        producer.onData(bb)

        bb.putLong(0, l * l + 1000_000_000L)
        producerWithTranslator.onData(bb)

        Thread.sleep(100)
    }
}

