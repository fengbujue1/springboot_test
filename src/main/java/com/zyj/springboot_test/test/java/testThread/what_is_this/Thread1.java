package com.zyj.springboot_test.test.java.testThread.what_is_this;

public class Thread1 extends Thread {
    @Override
    public void run() {
        System.out.println(this);
    }
}
