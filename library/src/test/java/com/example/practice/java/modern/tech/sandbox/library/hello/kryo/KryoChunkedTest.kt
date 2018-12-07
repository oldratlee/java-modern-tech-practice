package com.example.practice.java.modern.tech.sandbox.library.hello.kryo

import com.esotericsoftware.kryo.Kryo
import com.esotericsoftware.kryo.io.InputChunked
import com.esotericsoftware.kryo.io.OutputChunked
import org.junit.Test
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream

class KryoChunkedTest {
    @Test
    fun chunked() {
        val kryo = Kryo()
        kryo.references = true
        kryo.register(FooClass::class.java) // optional

        val foo = FooClass()
        foo.name = "Hello Kryo!"

        val baos = ByteArrayOutputStream()
        val output = OutputChunked(baos)
        kryo.writeClassAndObject(output, "hello chucked!")
        kryo.writeObject(output, "world chucked!")
        kryo.writeObject(output, foo)
        // if miss endChunks, cause underflow when first read latter!!
        output.endChunks()
        output.close()

        val input = InputChunked(ByteArrayInputStream(baos.toByteArray()))
        kryo.readClassAndObject(input).also {
            println(it)
        }
        kryo.readObject(input, String::class.java).also {
            println(it)
        }
        kryo.readObject(input, FooClass::class.java).also {
            println(it)
        }
        input.close()
    }
}
