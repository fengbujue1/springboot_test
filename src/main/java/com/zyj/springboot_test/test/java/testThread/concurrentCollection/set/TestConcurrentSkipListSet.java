package com.zyj.springboot_test.test.java.testThread.concurrentCollection.set;

import java.util.concurrent.ConcurrentSkipListSet;

public class TestConcurrentSkipListSet {
    public static void main(String[] args) {
        ConcurrentSkipListSet<String> concurrentSkipListSet = new ConcurrentSkipListSet<>();

        concurrentSkipListSet.add("123");
        concurrentSkipListSet.add("123");
        concurrentSkipListSet.add("456");

    }
}
