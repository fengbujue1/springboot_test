package com.zyj.springboot_test.test.java.testThread.test_atomicity_in_thread;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicIntegerArray;
import java.util.concurrent.atomic.LongAccumulator;
import java.util.function.LongBinaryOperator;

public class AtomicTypeInJUC {
    //原子更新整型
    static AtomicInteger num = new AtomicInteger(0);
    public static void main(String[] args) {
//        test1();
//        AtomicIntegerArray atomicIntegerArray;
        testAccumulator();
    }
    public static void testAccumulator() {
        //参数left是当前值，ringt是传入的值
        LongAccumulator longAccumulator = new LongAccumulator(new LongBinaryOperator() {
            @Override
            public long applyAsLong(long left, long right) {
                return left;
            }
        }, 0);
        longAccumulator.accumulate(1);
        System.out.println(longAccumulator.get());
    }

    public static void test1() {

        CountDownLatch countDownLatch = new CountDownLatch(6);
        for (int i = 0; i < 6; i++) {
            new Thread() {
                @Override
                public void run() {
                    for (int i = 0; i < 10000; i++) {
                        num.incrementAndGet();
                    }
                    System.out.println("end");
                    countDownLatch.countDown();
                }
            }.start();
        }
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(num);
    }
}
