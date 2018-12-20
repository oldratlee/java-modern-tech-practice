package com.example.practice.java.modern.tech.sandbox.library.hello.kryo.customized

import com.esotericsoftware.kryo.Kryo
import com.esotericsoftware.kryo.Serializer
import com.esotericsoftware.kryo.factories.ReflectionSerializerFactory
import com.esotericsoftware.kryo.io.Input
import com.esotericsoftware.kryo.io.Output
import com.esotericsoftware.kryo.serializers.CompatibleFieldSerializer
import com.example.practice.java.modern.tech.sandbox.library.hello.kryo.demo.LessMapReferenceResolver
import de.javakaffee.kryoserializers.*
import io.reactivex.Flowable
import org.reactivestreams.Publisher
import java.lang.reflect.InvocationHandler
import java.lang.reflect.Method
import java.util.*
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.ConcurrentMap
import java.util.concurrent.atomic.AtomicLong

fun main(args: Array<String>) {
    com.esotericsoftware.minlog.Log.TRACE()
    val kryo = getKryo()

    // round trip usage output/input for test
    // https://github.com/EsotericSoftware/kryo#round-trip
    val output = Output(1024, -1)
    kryo.writeObject(output,
            ShopCart("jerry",
                    Flowable.just(
                            Item("Item 1", Flowable.just(Tag("tag 1"), Tag("tag 2"))),
                            Item("Item 2", Flowable.just(Tag("tag 1"), Tag("tag 2")))
                    )))
    output.close()

    val input = Input(output.buffer, 0, output.position())
    kryo.readObject(input, ShopCart::class.java)!!.let { shopCart: ShopCart ->
        shopCart.itemFlow
                .flatMap { item ->
                    item.tagFlow.map {
                        "shopCart ${shopCart.user} - ${item.name} - ${it.name}"
                    }
                }
                .subscribe(::println)
    }
    input.close()
}

// Mocked Biz Pojo

data class Tag(val name: String) {
    // default constructor for serialization
    @Suppress("unused")
    private constructor() : this("")
}

data class Item(val name: String, val tagFlow: Flowable<Tag>) {
    // default constructor for serialization
    @Suppress("unused")
    private constructor() : this("", Flowable.empty())
}

data class ShopCart(val user: String, val itemFlow: Flowable<Item>) {
    // default constructor for serialization
    @Suppress("unused")
    private constructor() : this("", Flowable.empty())
}

private fun getKryo(): Kryo {
    val kryo = Kryo()
    kryo.references = true
    kryo.referenceResolver = LessMapReferenceResolver()
    kryo.setDefaultSerializer(ReflectionSerializerFactory(CompatibleFieldSerializer::class.java))
    // enable to log a message when an unregistered class is encountered
    kryo.isWarnUnregisteredClasses = true

    // Default Serializer
    // Publisher Serializer
    kryo.addDefaultSerializer(Publisher::class.java, PublisherSerializer())

    // Serializer
    // https://github.com/magro/kryo-serializers
    kryo.register(Arrays.asList("")::class.java, ArraysAsListSerializer())
    kryo.register(GregorianCalendar::class.java, GregorianCalendarSerializer())
    kryo.register(InvocationHandler::class.java, JdkProxySerializer())
    UnmodifiableCollectionsSerializer.registerSerializers(kryo)
    SynchronizedCollectionsSerializer.registerSerializers(kryo)

    return kryo
}

class PublisherSerializer : Serializer<Publisher<*>>() {
    override fun write(kryo: Kryo, output: Output, obj: Publisher<*>) {
        val flowId = FlowManager.addFlowObj(obj)
        output.writeLong(flowId)
        output.writeString("127.0.0.1")
        output.writeInt(20880, true)
    }

    override fun read(kryo: Kryo, input: Input, type: Class<Publisher<*>>): Publisher<*> {
        val flowId = input.readLong()
        val ip = input.readString()
        val port = input.readInt(true)
        return FlowManager.getFlowObj(flowId) as Publisher<*>
    }
}

object FlowManager {
    private val flowObj2Id: ConcurrentMap<Publisher<*>, Long> = ConcurrentHashMap()
    private val id2flowObj: ConcurrentMap<Long, Publisher<*>> = ConcurrentHashMap()

    fun addFlowObj(flow: Publisher<*>): Long {
        val flowId = nextFlowId()
        flowObj2Id.putIfAbsent(flow, flowId)
        id2flowObj.putIfAbsent(flowId, flow)
        return flowId
    }

    fun removeFlowObj(flow: Publisher<*>): Unit {
        flowObj2Id.remove(flow)?.let {
            id2flowObj.remove(it)
        }
    }

    fun getFlowObj(flowId: Long) = id2flowObj[flowId]

    private val flowStubId: ConcurrentMap<Any, Long> = ConcurrentHashMap()
    private val id2flowStub: ConcurrentMap<Long, Any> = ConcurrentHashMap()

    fun addFlowStub(flow: Any, flowId: Long) {
        flowStubId.putIfAbsent(flow, flowId)
        id2flowStub.putIfAbsent(flowId, flow)
    }

    fun removeFlowStub(flow: Any): Unit {
        flowStubId.remove(flow)?.let {
            id2flowStub.remove(it)
        }
    }

    private val idGenerator = AtomicLong()
    private fun nextFlowId() = idGenerator.getAndIncrement()
}

interface ActorReference {
    fun getIp(): String
    fun getPort(): Int
}

@Suppress("ArrayInDataClass")
data class IMA(val serviceType: Class<*>, val method: Method, val args: Array<Any>)



