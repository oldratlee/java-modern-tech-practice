package com.example.practice.java.modern.tech.sandbox.library.hello.lambda;

/**
 * Lambda class like {@code FullQualifiedEnclosingClassName$$Lambda$<Num1>/<Num2>},
 * <p>
 * {@code <Num1>} is related to
 * <ul>
 * <li>order of lambda in enclosing class</li>
 * <li>jdk version</li>
 * </ul>
 * <p>
 * {@code <Num2>} is related to
 * <ul>
 * <li>the machine</li>
 * <li>jdk version</li>
 * </ul>
 * <p>
 * some example:
 * <ul>
 * <li>on jdk 8: <br>
 * {@code com.example.practice.java.modern.tech.sandbox.library.hello.lambda.LambdaClassNameDemo$$Lambda$3/195600860}</li>
 * <li>on jdk 11: <br>
 * {@code com.example.practice.java.modern.tech.sandbox.library.hello.lambda.LambdaClassNameDemo$$Lambda$14/0x0000000800066840}</li>
 * </ul>
 */
public class LambdaClassNameDemo {
    public static void main(String[] args) {
        Runnable r1 = () -> System.out.println("Hello");
        Runnable r2 = () -> System.out.println("Hello");
        Runnable r_anonymousClass = new Runnable() {
            @Override
            public void run() {
                System.out.println("Hello");
            }
        };
        Runnable r3 = () -> System.out.println("Hello world");
        Runnable r4 = () -> System.out.println("Hello world");


        System.out.println(r4.getClass().getName());
        System.out.println(r3.getClass().getName());

        System.out.println(r1.getClass().getName());
        System.out.println(r_anonymousClass.getClass().getName());
        System.out.println(r2.getClass().getName());
    }
}
