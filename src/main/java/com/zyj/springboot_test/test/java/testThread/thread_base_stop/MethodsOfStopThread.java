package com.zyj.springboot_test.test.java.testThread.thread_base_stop;

public class MethodsOfStopThread {
    public static void main(String[] args) throws Exception{
//        testStopMethod();
//        testInterrupt();
        testLockBlockInterrupt();
    }


    /**
     * 测试stop方法
     * 该方法会破坏操作的原子性
     */
    private static void testStopMethod() throws Exception{
        MyThread thread = new MyThread();
        thread.start();

        Thread.sleep(1000);     // 休眠1秒，确保i变量自增成功
//        thread.interrupt();
        thread.stop();//-----标量i增加成功，变量j没有增加成功，如果这两个操作是绑定在一起的原子操作，那么这个stop方法就破坏了操作的原子性

        while (thread.isAlive()) {}  // 确保线程已经终止
        thread.print();     // 输出结果
    }

    private static void testInterrupt() throws Exception{
        MyThread2 thread = new MyThread2();
        thread.start();
        Thread.sleep(1000);
        System.out.println("打断之前线程的状态"+thread.getState().toString());
        System.out.println("打断之前的interrupt标记"+thread.isInterrupted());
        thread.interrupt();
        System.out.println("打断之后线程的状态"+thread.getState().toString());
        System.out.println("打断之后的interrupt标记"+thread.isInterrupted());
    }

    private static void testLockBlockInterrupt() throws Exception {
        Object lock = new Object();
        TestLockBlockInterrupt thread1 = new TestLockBlockInterrupt(lock, "thread1");
        thread1.start();
        Thread.sleep(1000);

        TestLockBlockInterrupt thread2 = new TestLockBlockInterrupt(lock, "thread2");
        thread2.start();

        Thread.sleep(1000);
        System.out.println("打断thread2");
        thread2.interrupt();

    }
}


class MyThread extends Thread {
    private int i = 0, j = 0;

    @Override
    public void run() {
        synchronized (this) {
            ++i;
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            ++j;
        }
        System.out.println("锁释放。。。");
        print();
    }

    public void print() {
        System.out.println("i=" + i + " j=" + j);
    }
}

class MyThread2 extends Thread {

    @Override
    public void run() {
        int i = 0;
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("抛出异常后interrupt标记"+this.isInterrupted());
        System.out.println("抛出异常后线程的状态"+this.getState().toString());
        while (i<5) {
            System.out.println("while循环");
            i++;
        }
    }
}

class TestLockBlockInterrupt extends Thread {
    private Object lock;
    private String name;

    public TestLockBlockInterrupt(Object lock,String name) {
        this.name = name;
        this.lock = lock;
    }

    @Override
    public void run() {
        System.out.println(name+"尝试获取锁");
        synchronized (lock) {
            System.out.println(name+"获取锁，进入睡眠，不会释放锁");
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }
}