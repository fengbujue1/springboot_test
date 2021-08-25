package com.zyj.springboot_test.test.java.testThread.concurrentCollection.set;

import java.util.HashSet;
import java.util.Iterator;

public class TestHashSet {
    public static void main(String[] args) {
        //底层通过hashmap的 key的唯一新进行去重。线程不安全

        HashSet<String> set = new HashSet<>();
        System.out.println(set.add("123"));;
        System.out.println(set.add("123"));;
        System.out.println(set.add("456"));;

        Iterator<String> iterator = set.iterator();
        while (iterator.hasNext()) {
            System.out.println(iterator.next());
        }
    }
}
