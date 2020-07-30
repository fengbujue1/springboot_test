package com.zyj.springboot_test.test.java.testThread.concurrentUtil.demo;

import java.util.concurrent.BrokenBarrierException;

public class MyCyclicBarrier_Test {
    public static void main(String args[]) throws BrokenBarrierException, InterruptedException {
        MyCyclicBarrier barrier = new MyCyclicBarrier(4);

        for (int i=0; i<10; i++){
            Thread th = new Thread(){
                @Override
                public void run() {
                    barrier.await();
                    System.out.println("run...");
                }
            };

            th.start();
            Thread.sleep(1000);
        }

    }



}
