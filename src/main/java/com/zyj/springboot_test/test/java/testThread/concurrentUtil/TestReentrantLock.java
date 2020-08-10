package com.zyj.springboot_test.test.java.testThread.concurrentUtil;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

public class TestReentrantLock {
    public static void main(String[] args) {
//        test1();
//        test1();
//        test3();.
        int[] ints = divingBoard(1, 1, 0);
        for (int i = 0; i < ints.length; i++) {
            System.out.println(ints[i]);
        }

    }
    public static int[] divingBoard(int shorter, int longer, int k) {
        if (k == 0) {
            return new int[0];
        }

        HashSet<Integer> integers = new HashSet<>();
        for (int s = 0; s <= k; s++) {
            int sNum = s;
            int lNum = k - s;
            integers.add(sNum * shorter + lNum* longer);
        }
        Iterator<Integer> iterator = integers.iterator();
        int[] reults = new int[integers.size()];
        Object[] objects = integers.toArray();
        for (int i = 0; i < objects.length; i++) {
            reults[i] = (int) objects[i];
        }
        Arrays.sort(reults);
        return reults;
    }


    /*
    阻塞状态下的线程状态：waiting状态
     */
    public static void test3() {
        ReentrantLock reentrantLock = new ReentrantLock();
//        MyReentrantLock reentrantLock = new MyReentrantLock();
        Thread thread1 = new Thread(new Runnable() {
            @Override
            public void run() {
                reentrantLock.lock();
                System.out.println("线程1获取锁,睡5秒");
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
               finally {
                    reentrantLock.unlock();
                }
            }
        });

        Thread thread2 = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("线程2调用lock方法获取锁，会被阻塞");
                reentrantLock.lock();
                System.out.println("线程2恢复运行");
            }
        });
        System.out.println("主线程开启线程1");
        thread1.start();
        try {
            System.out.println("主线程睡1秒后开启线程2");
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        thread2.start();
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("此时线程2 的状态" + thread2.getState());
    }

    /*
    打断通过LockInterrupt方法获取锁的线程，并不会发生什么
     */
    public static void test2() {
        ReentrantLock reentrantLock = new ReentrantLock();
        reentrantLock.lock();
        Thread thread1 = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("线程1,通过lockInterrupt方法获取锁,睡10秒");
                try {
                    reentrantLock.lockInterruptibly();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                try {
                    Thread.sleep(10000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        Thread thread2 = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("线程2调用lock方法，会被阻塞");
                reentrantLock.lock();
                System.out.println("线程2恢复运行");
            }
        });
        thread1.start();
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("主线程睡了2秒后开启线程2");
        thread2.start();


        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("主线程又睡了3秒后打断已经获取到锁的线程1");
        thread1.interrupt();
    }

    /**
     * 基本用法
     * lock().tryLock(),tryLock(Time),
     * lockInterruptibly（）在等待锁的过程中可被打断，恢复线程运行
     */
    public static void test1() {
        ReentrantLock reentrantLock = new ReentrantLock();
        reentrantLock.lock();
        Thread thread1 = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("线程1获取锁,睡10秒");
                reentrantLock.lock();
                try {
                    Thread.sleep(10000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        Thread thread2 = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("线程2调用tryLock方法，尝试获取锁，不会被阻塞");
                boolean result = reentrantLock.tryLock();
                System.out.println("线程2获取的结果：" + result);
            }
        });
        Thread thread3 = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("线程3调用lock方法，会被阻塞");
                reentrantLock.lock();
                System.out.println("没被阻塞");
            }
        });

        Thread thread4 = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("线程4调用trylock（time）方法，会被阻塞一定时间(2秒)");
                boolean result = false;
                try {
                    result = reentrantLock.tryLock(2,TimeUnit.SECONDS);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("线程4阻塞结束，结果：" + result);
            }
        });
        Thread thread5 = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("线程5调用lockInterruptibly方法，会被阻塞，但是可打断");
                boolean result = false;
                try {
                    reentrantLock.lockInterruptibly();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("线程5的阻塞状态被外部打断，程序继续执行");
            }
        });

        thread1.start();
        try {
            System.out.println("主线程睡1秒后开启线程2,3,4,5");
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        thread2.start();
        thread3.start();
        thread4.start();
        thread5.start();


        try {
            Thread.sleep(3000);
            System.out.println("主线程睡5秒后打断线程5");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        thread5.interrupt();

        try {
            System.out.println("主线程睡3秒后打断线程3");
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("主线程打断 线程3的阻塞状态，看看会发生什么");//什么都不会发生，无法打断
        thread3.interrupt();
    }


}
