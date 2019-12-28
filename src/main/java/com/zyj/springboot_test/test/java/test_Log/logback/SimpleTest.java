package com.zyj.springboot_test.test.java.test_Log.logback;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SimpleTest {
    public static void main(String[] args) {
        Logger logger = LoggerFactory.getLogger(SimpleTest.class);
        logger.info("hello");
        logger.debug("hello");
        logger.warn("asd");
    }
}
