package com.zyj.springboot_test.test.java.testThread.concurrentUtil.demo;

public class MySemaphore_Test {

    public static void main(String args[]) throws InterruptedException {
        MySemaphore sp = new MySemaphore(3);

        for (int i=0;i< 10; i++){
            new Thread(){
                @Override
                public void run() {
                    sp.acquire();
                    System.out.println(" here i am...");
                    try {
                        Thread.sleep(3000L);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    sp.release();
                }
            }.start();
            Thread.sleep(10L);
        }

    }



}
