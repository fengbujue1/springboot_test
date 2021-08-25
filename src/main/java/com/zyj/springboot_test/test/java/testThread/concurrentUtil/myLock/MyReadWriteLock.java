package com.zyj.springboot_test.test.java.testThread.concurrentUtil.myLock;

import java.util.concurrent.locks.ReentrantReadWriteLock;

public class MyReadWriteLock {
    public static void main(String[] args) {
        ReentrantReadWriteLock reentrantReadWriteLock = new ReentrantReadWriteLock();

        ReentrantReadWriteLock.ReadLock readLock = reentrantReadWriteLock.readLock();
        ReentrantReadWriteLock.WriteLock writeLock = reentrantReadWriteLock.writeLock();

        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("线程：" + Thread.currentThread().getName() + "准备获取写锁");
                writeLock.lock();
                System.out.println("线程：" + Thread.currentThread().getName() + "获取写锁成功");
                try {
                    Thread.sleep(6000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                System.out.println("线程：" + Thread.currentThread().getName() + "准备释放写锁");
                writeLock.unlock();
                System.out.println("线程：" + Thread.currentThread().getName() + "获取释放写锁成功");
            }
        }).start();

        for (int i = 0; i < 5; i++) {
            new Thread(new Runnable() {
                @Override
                    public void run() {
                    System.out.println("线程：" + Thread.currentThread().getName() + "准备获取读锁");
                    readLock.lock();
                    System.out.println("线程：" + Thread.currentThread().getName() + "获取读锁成功");
                    try {
                        Thread.sleep(6000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println("线程：" + Thread.currentThread().getName() + "准备释放读锁");
                    readLock.unlock();
                    System.out.println("线程：" + Thread.currentThread().getName() + "释放读锁成功");
                }
            }).start();
        }
        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("线程：" + Thread.currentThread().getName() + "准备获取写锁");
                writeLock.lock();
                System.out.println("线程：" + Thread.currentThread().getName() + "获取写锁成功");
                try {
                    Thread.sleep(6000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                System.out.println("线程：" + Thread.currentThread().getName() + "准备释放写锁");
                writeLock.unlock();
                System.out.println("线程：" + Thread.currentThread().getName() + "获取释放写锁成功");
            }
        }).start();

        for (int i = 0; i < 5; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    System.out.println("线程：" + Thread.currentThread().getName() + "准备获取读锁");
                    readLock.lock();
                    System.out.println("线程：" + Thread.currentThread().getName() + "获取读锁成功");
                    try {
                        Thread.sleep(6000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println("线程：" + Thread.currentThread().getName() + "准备释放读锁");
                    readLock.unlock();
                    System.out.println("线程：" + Thread.currentThread().getName() + "释放读锁成功");
                }
            }).start();
        }

        System.out.println("main end");
    }

}
