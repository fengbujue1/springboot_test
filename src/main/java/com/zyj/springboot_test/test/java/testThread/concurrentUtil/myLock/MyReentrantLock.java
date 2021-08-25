package com.zyj.springboot_test.test.java.testThread.concurrentUtil.myLock;

import java.util.Iterator;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.LockSupport;
import java.util.concurrent.locks.ReentrantLock;

public class MyReentrantLock {

    public static void main(String[] args) {
        ReentrantLock reentrantLock2 = new ReentrantLock();
        MyReentrantLock reentrantLock = new MyReentrantLock();
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

    private Thread owner = null;//这个锁的当前持有线程
    private LinkedBlockingQueue<Thread> waiters=new LinkedBlockingQueue<>();//等待队列
    private AtomicInteger count = new AtomicInteger(0);//计数器,owner持有的次数


    public boolean tryLock() {
        int ct = count.get();
        if (ct != 0) {
            if (owner == Thread.currentThread()) {
                count.set(ct + 1);
                return true;
            }
            return false;
        } else {
            if (count.compareAndSet(ct, ct + 1)) {
                owner = Thread.currentThread();
                return true;
            }
            return false;
        }
    }

    public void lock() {
        while (!tryLock()) {
            waiters.offer(Thread.currentThread());
            LockSupport.park();
            waiters.remove(Thread.currentThread());
        }
    }

    public void unlock() {


        if (tryUnlock()) {
            Iterator<Thread> iterator = waiters.iterator();
            while (iterator.hasNext()) {
                Thread next = iterator.next();
                LockSupport.unpark(next);
            }
        }
    }
    private boolean tryUnlock() {
        int ct = count.get();
        if (owner == Thread.currentThread()) {
            int temp = ct - 1;
            count.compareAndSet(ct, ct - 1);
            if (temp == 0) {
                owner = null;
                return true;
            }
            return false;
        }
        throw new IllegalMonitorStateException();
    }

}
