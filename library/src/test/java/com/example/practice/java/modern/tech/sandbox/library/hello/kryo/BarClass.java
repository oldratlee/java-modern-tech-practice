package com.example.practice.java.modern.tech.sandbox.library.hello.kryo;

class BarClass {
//    String xx;
    String value;

    public String getValue() {
        return value;
    }

    @SuppressWarnings("unused")
    public BarClass() {
    }

    @SuppressWarnings("WeakerAccess")
    public BarClass(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "BarClass{value='" + value + '\'' + '}';
    }
}
