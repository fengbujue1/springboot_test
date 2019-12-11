package com.zyj.springboot_test.test.testThread;


public class TestThradLocal {
    public static void main(String[] args) {
        ThreadLocal<String> stringThreadLocal = new ThreadLocal<>();
        stringThreadLocal.set("asd");
        new Thread() {
            @Override
            public void run() {
                stringThreadLocal.set("thread1");
                System.out.println(stringThreadLocal.get());
            }
        }.start();
        new Thread() {
            @Override
            public void run() {
                stringThreadLocal.set("thread2");
                System.out.println(stringThreadLocal.get());
            }
        }.start();
    }
}
