package com.zyj.springboot_test.test.java.testThread.concurrentUtil;

import java.util.Random;
import java.util.concurrent.CyclicBarrier;

public class TestCyclicbarrier {
    public static void main(String[] args) {
        CyclicBarrier cyclicBarrier = new CyclicBarrier(3, new Runnable() {
            @Override
            public void run() {
                System.out.println("doSomething after all arive");
            }
        });
        for (int i = 0; i < 3; i++) {
            new Thread(new Task(cyclicBarrier)).start();
        }
        System.out.println("main end");

    }
    private static class Task implements Runnable{
        private CyclicBarrier cyclicBarrier;
        public Task(CyclicBarrier cyclicBarrier) {
            this.cyclicBarrier = cyclicBarrier;
        }

        @Override
        public void run() {
            Random random = new Random();
            int randTime = random.nextInt(3000);
            try {
                Thread.sleep(randTime);
                System.out.println("arrive step1:" + Thread.currentThread());
                cyclicBarrier.await();
                System.out.println("doSometing together1");

                Thread.sleep(randTime);
                System.out.println("arrive step2:" + Thread.currentThread());
                cyclicBarrier.await();
                System.out.println("doSometing together2");


                Thread.sleep(randTime);
                System.out.println("arrive step3:" + Thread.currentThread());
                cyclicBarrier.await();
                System.out.println("doSometing together3");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}

