package com.zyj.springboot_test.test.java.testThread.test_callable;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.locks.LockSupport;

public class MyFutureTask<T> implements Runnable{//继承Runnable接口，通过Thread类开启新线程执行任务
    private Callable<T> task;//采用组合的方式持有Callable实例
    private BlockingQueue<Thread> waiters=new LinkedBlockingDeque<>();
    private T result; //支持泛型
    private volatile int state;//状态 在这个简易的FutureTask里面感觉用不着，状态感觉是需要带
    // 取消功能的时候使用，
    // 以及异常抛出的时候

    public MyFutureTask(Callable task) {
        this.task = task;
    }

    @Override
    public void run() {
        if (task != null) {
            try {
                result = task.call();
            } catch (Exception e) {
                e.printStackTrace();
            }finally {
                while (!waiters.isEmpty()) {
                    Thread waiter = waiters.poll();
                    LockSupport.unpark(waiter);//唤醒所有正在等待结果的线程
                }
            }
        }
    }

    public T getResult() {
        while (result == null) {
            if (!waiters.contains(Thread.currentThread())) {//避免伪唤醒
                waiters.add(Thread.currentThread());
            }
            System.out.println("结果还未产生，进入阻塞等待");
            LockSupport.park();
        }
        return result;

    }
}
