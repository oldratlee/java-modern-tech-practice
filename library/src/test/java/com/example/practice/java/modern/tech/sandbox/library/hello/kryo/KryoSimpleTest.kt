package com.example.practice.java.modern.tech.sandbox.library.hello.kryo

import com.esotericsoftware.kryo.Kryo
import com.esotericsoftware.kryo.io.Input
import com.esotericsoftware.kryo.io.Output
import org.junit.Test

class KryoSimpleTest {
    @Test
    fun helloWorld__knowClass_objectNotNull() {
        val kryo = Kryo()
        kryo.references = true
        kryo.register(FooClass::class.java) // optional

        val foo = FooClass()
        foo.name = "Hello Kryo!"

        // round trip usage output/input for test
        // https://github.com/EsotericSoftware/kryo#round-trip
        val output = Output(1024, -1)
        kryo.writeObject(output, foo)
        output.close()

        val input = Input(output.buffer, 0, output.position())
        val object2 = kryo.readObject(input, FooClass::class.java)
        println(object2)
        input.close()
    }

    @Test
    fun unknownClass_objectMayNull() {
        val kryo = Kryo()
        kryo.references = true
        kryo.register(FooClass::class.java) // optional

        val `object` = FooClass()
        `object`.name = "Hello Kryo! ClassAndObject"

        val output = Output(1024, -1)
        kryo.writeClassAndObject(output, `object`)
        output.close()

        val input = Input(output.buffer, 0, output.position())
        val object2 = kryo.readClassAndObject(input)
        println(object2)
        input.close()
    }

    @Test
    fun knowClass_objectMayNull() {
        val kryo = Kryo()
        kryo.references = true
        kryo.register(FooClass::class.java) // optional

        val `object` = FooClass()
        `object`.name = "Hello Kryo! ClassAndObject"

        val output = Output(1024, -1)
        kryo.writeObjectOrNull(output, `object`, FooClass::class.java)
        output.close()

        val input = Input(output.buffer, 0, output.position())
        val object2 = kryo.readObjectOrNull(input, FooClass::class.java)

        println(object2)
        input.close()
    }
}
