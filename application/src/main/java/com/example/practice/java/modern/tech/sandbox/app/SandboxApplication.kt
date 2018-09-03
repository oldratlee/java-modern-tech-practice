package com.example.practice.java.modern.tech.sandbox.app

import com.example.practice.java.modern.tech.sandbox.library.hello.HelloService
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@SpringBootApplication(scanBasePackages = [
    "com.example.practice.java.modern.tech.sandbox.app",
    "com.example.practice.java.modern.tech.sandbox.library.hello"
])
class SandboxApplication

@RestController
class DemoApplication(private val helloService: HelloService) {
    @GetMapping("/")
    fun home(): String {
        return helloService.message()
    }
}

fun main(args: Array<String>) {
    runApplication<SandboxApplication>(*args)
}

