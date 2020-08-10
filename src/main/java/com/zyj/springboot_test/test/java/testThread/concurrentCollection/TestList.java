package com.zyj.springboot_test.test.java.testThread.concurrentCollection;

import java.util.NavigableSet;
import java.util.Random;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CopyOnWriteArraySet;

public class TestList {
    public static void main(String[] args) {
        new CopyOnWriteArrayList<>();//线程安全的arrayList

    }
}
