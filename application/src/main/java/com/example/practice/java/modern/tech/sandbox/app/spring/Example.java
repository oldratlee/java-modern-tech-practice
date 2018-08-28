package com.example.practice.java.modern.tech.sandbox.app.spring;

import org.springframework.boot.*;
import org.springframework.boot.autoconfigure.*;
import org.springframework.web.bind.annotation.*;

// https://docs.spring.io/spring-boot/docs/current/reference/html/getting-started-first-application.html#getting-started-first-application-code
@RestController
@EnableAutoConfiguration
public class Example {

	@RequestMapping("/first")
	String home() {
		return "Hello World, first!";
	}

	public static void main(String[] args) throws Exception {
		SpringApplication.run(Example.class, args);
	}

}
