package com.zyj.springboot_test.test.java.testThread.testSynchronzied;

import java.text.SimpleDateFormat;
import java.util.Date;

public class synchroizeObj {
    public static int i = 0;
    public static void main(String[] args) throws InterruptedException {
        for (int i = 0; i < 10; i++) {
            new Thread(new Task()).start();
        }
        Thread.sleep(3000);
        System.out.println(i);
    }

    public static void doSome() {
        Date date = new Date();
        SimpleDateFormat df = new SimpleDateFormat("yyyymmdd");
        String format = df.format(date);
        synchronized (format + ".sequence") {
            i++;
        }
    }
}
class Task implements Runnable {
    @Override
    public void run() {
        for (int i = 0; i < 100; i++) {
            synchroizeObj.doSome();
        }
    }
}
