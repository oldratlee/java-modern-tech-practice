package com.example.practice.java.modern.tech.sandbox.library.hello;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class HelloServiceTest {

    @Autowired
    private HelloService helloService;

    @Test
    public void contextLoads() {
        assertThat(helloService.message()).isEqualTo("hello world");
    }

    @SpringBootApplication(scanBasePackageClasses = HelloService.class)
    static class TestApp {
    }

}
