package com.zyj.springboot_test.test.java.testThread.concurrentCollection;

import java.util.NavigableSet;
import java.util.Random;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.concurrent.CopyOnWriteArraySet;

public class TestSet {
    public static void main(String[] args) {

        new CopyOnWriteArraySet<>();//线程安全的set,底层通过CopyOnWriteArrayList实现



        ConcurrentSkipListSet<Integer> concurrentSkipListSet
                = new ConcurrentSkipListSet<>();//线程安全且，排序好的，查询速度快的Set,
        Random random = new Random();
        for (int i = 0; i < 100; i++) {
            concurrentSkipListSet.add(random.nextInt(100));
        }
        System.out.println(concurrentSkipListSet.size());
        System.out.println(concurrentSkipListSet);
        System.out.println(concurrentSkipListSet.subSet(1,20));
        NavigableSet<Integer> integers = concurrentSkipListSet.subSet(1, 20);//范围分段
        System.out.println(integers.lower(10));//分段的结果查找距离某个数字最近的数字
        System.out.println(concurrentSkipListSet.descendingSet());//倒叙输出
    }
}
