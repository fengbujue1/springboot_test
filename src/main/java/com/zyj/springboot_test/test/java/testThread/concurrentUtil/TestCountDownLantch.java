package com.zyj.springboot_test.test.java.testThread.concurrentUtil;

import java.util.concurrent.CountDownLatch;

public class TestCountDownLantch {
    public static void main(String[] args) {
        CountDownLatch countDownLatch = new CountDownLatch(4);

        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("1");
                try {
                    Thread.sleep(1000);
                    countDownLatch.countDown();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("count down---1");
            }
        }).start();
        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("2");
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("count down---2");
                countDownLatch.countDown();
            }
        }).start();
        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("3");
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("count down---3");
                countDownLatch.countDown();
            }
        }).start();
        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("4");
                try {
                    Thread.sleep(4000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("count down---4");
                countDownLatch.countDown();
            }
        }).start();


        try {
            System.out.println("main wait");
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
            System.out.println("main continue");
    }
}
