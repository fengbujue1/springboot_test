package com.zyj.springboot_test.test.java.testThread.test_concurrentUtil;

import java.util.concurrent.Semaphore;

public class TestSemaPhroe {
    public static void main(String[] args) {
        Semaphore semaphore = new Semaphore(3);
        for (int i = 0; i < 5; i++) {
            new Thread(new Task2(semaphore)).start();
        }
        for (int i = 0; i < 5; i++) {
            new Thread(new Task(semaphore)).start();
        }

    }

    private static class Task implements Runnable {
        private Semaphore semaphore;

        public Task(Semaphore semaphore) {
            this.semaphore = semaphore;
        }

        @Override
        public void run() {
            try {
                System.out.println(Thread.currentThread() + "请求令牌");
                while (!semaphore.tryAcquire()) {
                    System.out.println(Thread.currentThread() + "没有获得令牌，尝试做其他的");
                    Thread.sleep(500);
                }
                System.out.println(Thread.currentThread() + "获取到令牌，继续执行");
                semaphore.release();
                System.out.println(Thread.currentThread() + "归还令牌");
            } catch (Exception e) {

            }


        }
    }
    private static class Task2 implements Runnable {
        private Semaphore semaphore;

        public Task2(Semaphore semaphore) {
            this.semaphore = semaphore;
        }

        @Override
        public void run() {
            try {
                System.out.println(Thread.currentThread() + "请求令牌");
                semaphore.acquire();
                System.out.println(Thread.currentThread() + "获取到令牌，继续执行");
                Thread.sleep(4000);
                semaphore.release();
                System.out.println(Thread.currentThread() + "归还令牌");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
