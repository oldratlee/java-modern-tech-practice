package com.example.practice.java.modern.tech.sandbox.library.hello.jboss_marshalling

import io.netty.handler.codec.marshalling.DefaultMarshallerProvider
import io.netty.handler.codec.marshalling.DefaultUnmarshallerProvider
import io.netty.handler.codec.marshalling.MarshallingDecoder
import io.netty.handler.codec.marshalling.MarshallingEncoder
import org.jboss.marshalling.Marshalling
import org.jboss.marshalling.MarshallingConfiguration


fun main(args: Array<String>) {
    val encoder = buildMarshallingEncoder()
    val decoder = buildMarshallingDecoder()
}

fun buildMarshallingDecoder(): MarshallingDecoder {
    val marshallerFactory = Marshalling.getProvidedMarshallerFactory("serial")

    val configuration = MarshallingConfiguration()
    configuration.version = 5

    val provider = DefaultUnmarshallerProvider(marshallerFactory, configuration)
    return MarshallingDecoder(provider, 1024)
}

fun buildMarshallingEncoder(): MarshallingEncoder {
    val marshallerFactory = Marshalling.getProvidedMarshallerFactory("serial")

    val configuration = MarshallingConfiguration()
    configuration.version = 5

    val provider = DefaultMarshallerProvider(marshallerFactory, configuration)
    return MarshallingEncoder(provider)
}

