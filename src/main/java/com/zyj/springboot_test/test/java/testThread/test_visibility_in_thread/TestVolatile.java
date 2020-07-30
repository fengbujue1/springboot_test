package com.zyj.springboot_test.test.java.testThread.test_visibility_in_thread;

import java.util.SortedSet;
import java.util.TreeSet;

public class TestVolatile {
    //volatile关键字，只能保证所修饰变量的可见性
    public static volatile long num = 0;
    volatile boolean isStop = false;
    public static void main(String[] args) {
//        TestVolatile testVolatile = new TestVolatile();
//        testVolatile.testVisibility();

    }
    public void testVisibility() {

        new Thread(()->{
            System.out.println("start");
            while (!isStop) {

            }
            System.out.println("stop");
        }).start();

        try {
            Thread.sleep(300);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("change tag");
        this.isStop = true;

    }

    public void testVoiatile() {
        new Thread() {
            @Override
            public void run() {
                for (int i = 0; i < 1000000; i++) {
                    num++;
                }
                System.out.println(num);
            }
        }.start();
        new Thread() {
            @Override
            public void run() {
                for (int i = 0; i < 1000000; i++) {
                    num++;
                }
                System.out.println(num);
            }
        }.start();
    }

}
