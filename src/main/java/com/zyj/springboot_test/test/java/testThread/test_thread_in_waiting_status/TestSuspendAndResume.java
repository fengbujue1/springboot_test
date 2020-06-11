package com.zyj.springboot_test.test.java.testThread.test_thread_in_waiting_status;

public class TestSuspendAndResume {
    public static Object lock = new Object();
    public static boolean flag = false;
    public static Thread thread1 = new Thread() {
        @Override
        public void run() {
            while (!flag) {
                System.out.println("线程1开始运行,并且进入等待");
                Thread.currentThread().suspend();
            }
            System.out.println("线程1重新开始运行");
        }
    };
    public static Thread thread2 = new Thread() {
        @Override
        public void run() {
            System.out.println("线程2开始运行，尝试唤起线程1");
            System.out.println("此时线程1的状态是：" + thread1.getState().toString());
            flag = true;
            thread1.resume();
        }
    };
    public static void main(String[] args) {
//        test1();
        test2();
//        test3();
    }

    /**
     * suspend 和 resume 方法的正常逻辑
     */
    public static void test1() {

        thread1.start();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        thread2.start();
    }

    /**
     * 死锁情况1：提前唤醒，通知丢失
     */
    public static void test2() {
        thread2.start();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        thread1.start();
    }
    /**
     * 死锁情况2：带着锁一起进入等待，没有释放，导致其他线程永远无法唤醒
     */
    public static void test3() {
        thread3.start();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        thread4.start();
    }
    public static Thread thread3 = new Thread() {
        @Override
        public void run() {
            synchronized (lock) {
                while (!flag) {//循环判定条件，防止伪唤醒
                    System.out.println("线程3开始运行,带着锁进入等待");
                    Thread.currentThread().suspend();
                }
                System.out.println("线程3重新开始运行");
            }
        }
    };
    public static Thread thread4 = new Thread() {
        @Override
        public void run() {
            System.out.println("线程4开始运行，尝试获取锁，然后唤醒线程1");
            flag = true;
            synchronized (lock) {
                thread1.resume();
            }
        }
    };
}
