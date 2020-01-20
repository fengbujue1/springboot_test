package com.zyj.springboot_test.test.java.testThread.test_atomicity_in_thread;

import sun.misc.Unsafe;

import java.lang.reflect.Field;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

public class UnsafeTest {
    public static Unsafe unsafe;
    public long offset;//偏移量
    private int i;

    public UnsafeTest() {
        //java不允许程序员使用这个类，安全性问题
//        unsafe = Unsafe.getUnsafe();

        //通过反射的方式获取
        try {
            //通过反射获取字段
            Field theUnsafe = Unsafe.class.getDeclaredField("theUnsafe");
            //设置可访问权限
            theUnsafe.setAccessible(true);
            //获取实例
            try {
                unsafe = (Unsafe) theUnsafe.get(null);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            //获取要修改的字段
            Field i = UnsafeTest.class.getDeclaredField("i");
            //获取偏移量
            offset = unsafe.objectFieldOffset(i);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
    }

    public void add() {
//        boolean  flag = false;
//        while (!flag) {
//            flag = unsafe.compareAndSwapInt(this, offset, i, ++i);
//        }

        //实现了原子性，在无锁情况下，多线程情况下，依然可以递增
        while (true) {
            if (unsafe.compareAndSwapInt(this, offset, i, i+1)) {
                return;
            }

        }
    }

    public int getI() {
        return i;
    }

    public static void main(String[] args) {
        CountDownLatch countDownLatch = new CountDownLatch(6);
        UnsafeTest unsafeTest = new UnsafeTest();
        for (int i = 0; i < 6; i++) {
            new Thread() {
                @Override
                public void run() {
                    for (int i = 0; i < 10000; i++) {
                        unsafeTest.add();
                    }
                    System.out.println("end");
                    System.out.println(unsafeTest.getI());
                    countDownLatch.countDown();
                }
            }.start();
        }
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(unsafeTest.getI());
    }
}
