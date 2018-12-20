package com.example.practice.java.modern.tech.sandbox.library.hello.kryo.demo

import com.esotericsoftware.kryo.util.MapReferenceResolver

class LessMapReferenceResolver : MapReferenceResolver() {
    override fun useReferences(type: Class<*>): Boolean =
            super.useReferences(type) &&
                    String::class.java != type &&
                    !type.isEnum
}
