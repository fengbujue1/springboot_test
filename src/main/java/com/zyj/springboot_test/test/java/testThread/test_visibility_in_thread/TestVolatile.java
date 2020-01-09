package com.zyj.springboot_test.test.java.testThread.test_visibility_in_thread;

public class TestVolatile {
    //volatile关键字，只能保证所修饰变量的可见性
    public static volatile long num = 0;

    public static void main(String[] args) {
        new Thread() {
            @Override
            public void run() {
                for (int i = 0; i < 1000000; i++) {
                    num++;
                }
                System.out.println(num);
            }
        }.start();
        new Thread() {
            @Override
            public void run() {
                for (int i = 0; i < 1000000; i++) {
                    num++;
                }
                System.out.println(num);
            }
        }.start();
    }

}
