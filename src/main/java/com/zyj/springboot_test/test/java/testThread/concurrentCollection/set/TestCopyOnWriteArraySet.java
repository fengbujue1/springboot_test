package com.zyj.springboot_test.test.java.testThread.concurrentCollection.set;

import java.util.concurrent.CopyOnWriteArraySet;

public class TestCopyOnWriteArraySet {
    public static void main(String[] args) {
        CopyOnWriteArraySet<String> copyOnWriteArraySet = new CopyOnWriteArraySet<>();
        copyOnWriteArraySet.add("123");
        copyOnWriteArraySet.add("123");
        copyOnWriteArraySet.add("456");


    }
}
