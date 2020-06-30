package com.zyj.springboot_test.test.java.testThread.test_thread_in_waiting_status;

import java.util.concurrent.locks.LockSupport;
import java.util.concurrent.locks.ReentrantLock;

public class TestParkAndUnpark {
    public static Object lock = new Object();
    public static ReentrantLock reentrantLock = new ReentrantLock();
    public static boolean flag = false;
    public static Thread thread1 = new Thread() {
        @Override
        public void run() {
            while (!flag) {
                System.out.println("线程1开始运行,并且进入等待");
                LockSupport.park();
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
            LockSupport.unpark(thread1);
        }
    };
    //重新定义两个thread,因为避免伪唤醒的措施，其实也防止了通知丢失的问题
    public static Thread thread1_2 = new Thread() {
        @Override
        public void run() {
            System.out.println("线程1开始运行,3s后进入等待");
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            LockSupport.park();
            System.out.println("线程1重新开始运行");
        }
    };
    public static Thread thread2_2 = new Thread() {
        @Override
        public void run() {
            System.out.println("线程2开始运行，尝试唤起线程1");
            System.out.println("此时线程1_2的状态是：" + thread1_2.getState().toString());
            LockSupport.unpark(thread1_2);
        }
    };
    public static Thread thread3 = new Thread() {
        @Override
        public void run() {
            synchronized (lock) {
                while (!flag) {//循环判定条件，防止伪唤醒
                    System.out.println("线程3开始运行,进入等待(不会释放syhchronize关键字持有的锁)");
                    System.out.println("lock：" + lock);
                    LockSupport.park();
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
            synchronized (lock) {
                flag = true;
                System.out.println("lock：" + lock);
                LockSupport.unpark(thread3);
            }
        }
    };
    public static Thread thread3_2 = new Thread() {
        @Override
        public void run() {
            reentrantLock.lock();
            while (!flag) {//循环判定条件，防止伪唤醒
                System.out.println("线程3开始运行,进入等待()");//此时线程3是waiting状态
                LockSupport.park();
            }
            System.out.println("线程3重新开始运行");
            reentrantLock.unlock();
        }
    };
    public static Thread thread4_2 = new Thread() {
        @Override
        public void run() {
            System.out.println("线程4开始运行，尝试获取锁，然后唤醒线程3_2");
            System.out.println("当前thread3_2的状态：" + thread3_2.getState().toString());
            reentrantLock.lock();
            flag = true;
            LockSupport.unpark(thread3_2);
            reentrantLock.unlock();
        }
    };
    public static void main(String[] args) {
//        test1();
//        test2();
//        test3();
        test4();
    }
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
     * 提前唤醒，不会造成通知丢失
     */
    public static void test2() {
        thread1_2.start();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        thread2_2.start();
    }
    /**
     * 死锁情况2：带着锁进入等待
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
    /**
     * 死锁情况3：带着可重入锁锁进入等待
     */
    public static void test4() {
        thread3_2.start();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        thread4_2.start();
    }
}
