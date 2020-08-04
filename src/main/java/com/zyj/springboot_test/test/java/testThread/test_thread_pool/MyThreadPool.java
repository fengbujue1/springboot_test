package com.zyj.springboot_test.test.java.testThread.test_thread_pool;

import io.netty.util.concurrent.DefaultThreadFactory;

import java.util.HashSet;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantLock;

public class MyThreadPool {
    public static void main(String[] args) throws InterruptedException {
        MyThreadPool myThreadPool = new MyThreadPool(new ArrayBlockingQueue<>(3), 3, 5, new ThreadFactory() {
            @Override
            public Thread newThread(Runnable r) {
                return new Thread(r);
            }
        });

        for (int i = 0; i < 20; i++) {
            myThreadPool.execute(new Runnable() {
                @Override
                public void run() {
                    System.out.println("task start");
                    try {
                        Thread.sleep(5000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println("task end");
                }
            });
        }

        Thread.sleep(20000);
        myThreadPool.shutdown();

    }

    private HashSet<Worker> workers;//工作队列(新线程的入队因为需要比较
    // 当前活跃的线程数量和最大线程数，所以需要在重入锁中进行，不需要做同步队列)

    private ReentrantLock lock;

    private BlockingQueue<Runnable> tasks;//任务队列
    private AtomicInteger activeThreads;//当前活跃的线程数
    private int coreSize;//核心线程数
    private int maxSize;//最大线程数
    private ThreadFactory threadFactory;//线程工厂
    private volatile boolean interrupted;

    public MyThreadPool(
            BlockingQueue<Runnable> tasks,
            int coreSize,
            int maxSize,
            ThreadFactory threadFactory
    ) {
        this.tasks = tasks;
        this.activeThreads = new AtomicInteger(0);
        this.coreSize = coreSize;
        this.maxSize = maxSize;
        this.threadFactory = threadFactory;
        workers = new HashSet<>();
        lock = new ReentrantLock();
        interrupted = false;
    }

    public void execute(Runnable task) {
        if (activeThreads.get() < coreSize) {
            addWorker(task);
        } else {
            if (activeThreads.get()>=maxSize) {
                System.out.println("拒绝");
            }
            if (!tasks.offer(task) && activeThreads.get() <= maxSize) {
                //队列满了，但是可以添加新的工作xiancheng
                addWorker(task);
            }


        }
    }

    public void shutdown() {
        interrupted = true;
        workers.forEach(worker -> {
            worker.thread.interrupt();
        });
    }


    private void addWorker(Runnable task) {
        lock.lock();

        try {
            if (activeThreads.get() < maxSize) {
                Worker worker = new Worker(task, threadFactory);
                worker.start();
                workers.add(worker);
                activeThreads.incrementAndGet();
            } else {
                    System.out.println("reject");
            }
        }finally {
            lock.unlock();
        }



    }
    class Worker implements Runnable{
        private Runnable task;
        private Thread thread;

        public Worker(Runnable task, ThreadFactory threadFactory) {
            thread = threadFactory.newThread(this);
            this.task = task;
        }

        public void start() {
            thread.start();
        }

        @Override
        public void run() {
            //首次任务执行
            Runnable job = task;
            task = null;

            for (; !interrupted; ) {
                try {
                    if (!interrupted && (job = tasks.take()) != null) {
                        job.run();
                    }
                    job = null;
                } catch (Exception e) {
                    System.out.println("exit in");
                }
            }
            System.out.println("exit out");
        }
    }
}
