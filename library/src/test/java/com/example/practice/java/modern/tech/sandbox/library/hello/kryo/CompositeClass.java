package com.example.practice.java.modern.tech.sandbox.library.hello.kryo;

class CompositeClass {
    FooClass foo;
    BarClass bar;

    public CompositeClass() {
    }

    public CompositeClass(FooClass foo, BarClass bar) {
        this.foo = foo;
        this.bar = bar;
    }

    @Override
    public String toString() {
        return "CompositeClass{foo=" + foo + ", bar=" + bar + '}';
    }
}
