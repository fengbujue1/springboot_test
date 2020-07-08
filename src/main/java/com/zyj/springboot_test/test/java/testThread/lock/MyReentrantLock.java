package com.zyj.springboot_test.test.java.testThread.lock;

import java.util.Iterator;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.LockSupport;

public class MyReentrantLock {
    private AtomicReference<Thread> owner = new AtomicReference<>();
    private LinkedBlockingQueue<Thread> waiters=new LinkedBlockingQueue<>();


    public boolean tryLock() {
        return owner.compareAndSet(null, Thread.currentThread());
    }

    public void lock() {
        while (!tryLock()) {
            waiters.offer(Thread.currentThread());
            LockSupport.park();
            waiters.remove(Thread.currentThread());
        }
    }

    public void unlock() {
        if (owner.compareAndSet(Thread.currentThread(),null)) {
            Iterator<Thread> iterator = waiters.iterator();
            while (iterator.hasNext()) {
                Thread next = iterator.next();
                LockSupport.unpark(next);
            }
        }
    }

}
