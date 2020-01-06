package com.zyj.springboot_test.test.java.testThread.what_is_this;

public class MainThread {
    public static void main(String[] args) {
        //多线程中的this代表的是线程自身这个实例
        new Thread1().start();
        new Thread2().start();
    }
}
