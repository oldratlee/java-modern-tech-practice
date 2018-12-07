package com.example.practice.java.modern.tech.sandbox.library.hello.kryo;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import org.junit.Test;

public class KryoObjectGraghTest {
    @Test
    public void register_simpleClass() {
        Output output = new Output(1024, -1);
        {
            Kryo kryo = new Kryo();
            kryo.setReferences(true);
            // enable to log a message when an unregistered class is encountered
            kryo.setWarnUnregisteredClasses(true);

            kryo.register(BarClass.class);
            kryo.register(FooClass.class); // optional
            System.out.printf("%s: %s %s: %s%n",
                    BarClass.class.getSimpleName(), kryo.getClassResolver().getRegistration(BarClass.class).getId(),
                    FooClass.class.getSimpleName(), kryo.getClassResolver().getRegistration(FooClass.class).getId()
            );

            kryo.writeObject(output, new FooClass("Hello Foo Kryo!"));
            kryo.writeObject(output, new BarClass("Hello Bar Kryo!"));

            output.close();
        }
        {
            Kryo kryo = new Kryo();
            kryo.setReferences(true);
            // enable to log a message when an unregistered class is encountered
            kryo.setWarnUnregisteredClasses(true);

            // register is DIFFERENT to input
            kryo.register(FooClass.class);
            kryo.register(BarClass.class);
            System.out.printf("%s: %s %s: %s%n",
                    BarClass.class.getSimpleName(), kryo.getClassResolver().getRegistration(BarClass.class).getId(),
                    FooClass.class.getSimpleName(), kryo.getClassResolver().getRegistration(FooClass.class).getId()
            );

            Input input = new Input(output.getBuffer(), 0, output.position());

            FooClass foo = kryo.readObject(input, FooClass.class);
            System.out.println(foo);

            final BarClass bar = kryo.readObject(input, BarClass.class);
            System.out.println(bar);

            input.close();
        }
    }

    @Test
    public void register_CompositeClass() {
        com.esotericsoftware.minlog.Log.TRACE();
        Output output = new Output(1024, -1);
        {
            Kryo kryo = new Kryo();
            kryo.setReferences(true);
            // enable to log a message when an unregistered class is encountered
            kryo.setWarnUnregisteredClasses(true);

//            kryo.register(BarClass.class);
//            kryo.register(FooClass.class); // optional
//            System.out.printf("%s: %s %s: %s%n",
//                    BarClass.class.getSimpleName(), kryo.getClassResolver().getRegistration(BarClass.class).getId(),
//                    FooClass.class.getSimpleName(), kryo.getClassResolver().getRegistration(FooClass.class).getId()
//            );

            kryo.writeObject(output, new CompositeClass(new FooClass("Hello Foo Kryo!"), new BarClass("Hello Bar Kryo!")));
            output.close();

            System.out.println("write!");
        }
        {
            Kryo kryo = new Kryo();
            kryo.setReferences(true);
            // enable to log a message when an unregistered class is encountered
            kryo.setWarnUnregisteredClasses(true);

            // register is DIFFERENT to input
            kryo.register(FooClass.class);
            kryo.register(BarClass.class);
            System.out.printf("%s: %s %s: %s%n",
                    BarClass.class.getSimpleName(), kryo.getClassResolver().getRegistration(BarClass.class).getId(),
                    FooClass.class.getSimpleName(), kryo.getClassResolver().getRegistration(FooClass.class).getId()
            );

            Input input = new Input(output.getBuffer(), 0, output.position());

            CompositeClass composite = kryo.readObject(input, CompositeClass.class);
            System.out.println(composite);
            System.out.println(composite.bar);
            final FooClass foo = composite.foo;
            System.out.println("foo: " + foo + " " + foo.name + " " + foo.getName());
            System.out.println((FooClass) (Object) foo);
            input.close();
        }
    }
}
