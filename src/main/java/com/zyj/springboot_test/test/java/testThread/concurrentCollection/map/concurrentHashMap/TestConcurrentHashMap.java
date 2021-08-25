package com.zyj.springboot_test.test.java.testThread.concurrentCollection.map.concurrentHashMap;

import java.util.concurrent.ConcurrentHashMap;

public class TestConcurrentHashMap {
    public static void main(String[] args) {
        ConcurrentHashMap<Object, Object> map = new ConcurrentHashMap<>();

        map.put("123", "123");
    }
}
