package com.example.practice.java.modern.tech.sandbox.library.lombok;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

@Slf4j
public class LombokTest {
    @Test
    public void test() {
        log.info("Hello lombok logger");
    }
}
