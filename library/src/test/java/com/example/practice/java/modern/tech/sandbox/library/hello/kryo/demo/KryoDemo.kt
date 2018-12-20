package com.example.practice.java.modern.tech.sandbox.library.hello.kryo.demo

import com.esotericsoftware.kryo.Kryo
import com.esotericsoftware.kryo.factories.ReflectionSerializerFactory
import com.esotericsoftware.kryo.io.Input
import com.esotericsoftware.kryo.io.Output
import com.esotericsoftware.kryo.serializers.CompatibleFieldSerializer
import java.io.FileInputStream
import java.io.FileOutputStream

fun main(args: Array<String>) {
    val kryo = Kryo()
    kryo.references = true
    kryo.referenceResolver = LessMapReferenceResolver()
    kryo.setDefaultSerializer(ReflectionSerializerFactory(CompatibleFieldSerializer::class.java))
    // enable to log a message when an unregistered class is encountered
    kryo.isWarnUnregisteredClasses = true
    kryo.register(HelloClass::class.java) // optional

    val fileName = "file.tmp"

    val output = Output(FileOutputStream(fileName))
    kryo.writeObject(output, HelloClass("Hello Kryo!", 18, HelloClass.Sex.FEMALE))
    output.close()

    val input = Input(FileInputStream(fileName))
    kryo.readObject(input, HelloClass::class.java).also { println(it) }
    input.close()
}

private data class HelloClass(var name: String?, var age: Int, val sex: Sex) {
    // default constructor for serialization
    @Suppress("unused")
    private constructor() : this(null, 0, Sex.FEMALE)

    enum class Sex {
        MALE, FEMALE
    }
}
