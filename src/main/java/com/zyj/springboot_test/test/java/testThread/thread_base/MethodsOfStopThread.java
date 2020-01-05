package com.zyj.springboot_test.test.java.testThread.thread_base;

public class MethodsOfStopThread {
    public static void main(String[] args) throws Exception{
        testStopMethod();
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
        thread.stop();

        while (thread.isAlive()) {}  // 确保线程已经终止
        thread.print();     // 输出结果
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
    }

    public void print() {
        System.out.println("i=" + i + " j=" + j);
    }
}