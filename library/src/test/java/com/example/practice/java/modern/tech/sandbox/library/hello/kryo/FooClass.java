package com.example.practice.java.modern.tech.sandbox.library.hello.kryo;

class FooClass {
    String name;

    public String getName() {
        return name;
    }

    FooClass() {
    }

    FooClass(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "HelloClass{name='" + name + '\'' + '}';
    }
}
