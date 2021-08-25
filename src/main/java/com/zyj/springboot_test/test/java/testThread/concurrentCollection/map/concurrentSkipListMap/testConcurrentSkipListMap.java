package com.zyj.springboot_test.test.java.testThread.concurrentCollection.map.concurrentSkipListMap;

import java.util.concurrent.ConcurrentSkipListMap;

public class testConcurrentSkipListMap {
    public static void main(String[] args) {
        ConcurrentSkipListMap<String, String> concurrentSkipListMap = new ConcurrentSkipListMap<>();
        concurrentSkipListMap.put("123", "123");
    }
}
