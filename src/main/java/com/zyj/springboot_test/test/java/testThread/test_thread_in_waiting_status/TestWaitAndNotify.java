package com.zyj.springboot_test.test.java.testThread.test_thread_in_waiting_status;

public class TestWaitAndNotify {
    public static Object lock = new Object();
    public static boolean flag = false;
    public static Thread thread1 = new Thread() {
        @Override
        public void run() {
            synchronized (lock) {
                while (!flag) {//循环判定条件，防止伪唤醒
                    System.out.println("线程1开始运行,并且进入等待");
                    System.out.println("lock：" + lock);
                    try {
                        lock.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println("线程1重新开始运行");
                }
            }
        }
    };
    public static Thread thread2 = new Thread() {
        @Override
        public void run() {
            synchronized (lock) {
                System.out.println("线程2开始运行，唤起线程1");
                System.out.println("lock：" + lock);
                System.out.println("当前thread1的状态：" + thread1.getState().toString());
                lock.notifyAll();
            }
        }
    };
    public static Thread thread3 = new Thread() {
        @Override
        public void run() {
            synchronized (lock) {
                while (!flag) {//循环判定条件，防止伪唤醒
                    System.out.println("线程3开始运行,进入等待（会释放锁）");
                    System.out.println("lock：" + lock);
                    try {
                        lock.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                System.out.println("线程3重新开始运行");
            }
        }
    };
    public static Thread thread4 = new Thread() {
        @Override
        public void run() {
            System.out.println("线程4开始运行，尝试获取锁，然后唤醒线程1");
            System.out.println("当前thread3的状态：" + thread3.getState().toString());
            flag = true;
            synchronized (lock) {
                System.out.println("lock：" + lock);
                lock.notifyAll();
            }
        }
    };
    public static void main(String[] args) {
        test1();
//        test2();
    }


    /**
     * 通知丢失， 此方法依然存在
     */
    public static void test1() {
        thread2.start();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        thread1.start();
    }
    /**
     * 不会出现带着锁进入等待的情况，因为进入等待之前会释放锁
     */
    public static void test2() {
        thread3.start();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        thread4.start();

    }
}
