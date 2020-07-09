package com.zyj.springboot_test.test.java.testThread.test_concurrentUtil;

import java.util.Random;
import java.util.concurrent.Phaser;

public class TestPhaser {
    public static void main(String[] args) {
        Phaser phaser = new Phaser(){
            @Override
            protected boolean onAdvance(int phase, int registeredParties) {
                switch (phase) {
                    case 0:
                        return print(registeredParties,phase);
                    case 1:
                        return print(registeredParties,phase);
                    case 2:
                        return print(registeredParties,phase);
                        default:
                            return true;
                }
            }

            private boolean print(int registeredParties,int phase) {
                System.out.println("阶段" + phase + "开始，注册线程数：" + registeredParties +
                        ",返回" + (registeredParties == 0) + "," + (registeredParties != 0 ? "当前段可以执行" : "所有流程执行完毕"));
                return registeredParties == 0;
            }

        };

        phaser.register();
        for (int i = 0; i < 3; i++) {
            new Thread(new Task(phaser)).start();
        }
        phaser.arriveAndAwaitAdvance();//第0阶段等待

        while (!phaser.isTerminated()) {
//            if (phaser.getPhase() == 1) {//这样的实现有问题，当main走到这一步的时候，可能其他线程已经完成了这一步，为了实现这种
//                //控制,需要让main也参与到 phaser中来，
//                System.out.println("main:检查到了阶段1,新增两个线程，完成后取消注册");
//                for (int i = 0; i < 2; i++) {
//                    new Thread(new Task2(phaser)).start();
//                }
//                break;
//            }
            if (phaser.getPhase() == 1) {
                System.out.println("main:检查到了阶段1,新增两个线程，完成后取消注册");
                phaser.bulkRegister(2);
                for (int i = 0; i < 2; i++) {
                    new Thread(new Task2(phaser)).start();
               }
                phaser.arriveAndAwaitAdvance();//第一阶段等待
            } else if (phaser.getPhase() == 2) {
                phaser.arriveAndAwaitAdvance();//第二阶段等待
                phaser.arriveAndDeregister();//第二阶段完，取消注册
            }
        }

    }

    private static class Task implements Runnable{
        private Phaser phaser;
        public Task(Phaser phaser) {
            this.phaser = phaser;
            phaser.register();
        }

        @Override
        public void run() {
            Random random = new Random();
            int randTime = random.nextInt(3000);
            try {
                Thread.sleep(randTime);
                System.out.println("arrive step0:" + Thread.currentThread());
                phaser.arriveAndAwaitAdvance();
                work0();


                Thread.sleep(randTime);
                System.out.println("arrive step1:" + Thread.currentThread());
                phaser.arriveAndAwaitAdvance();
                work1();


                Thread.sleep(randTime);
                System.out.println("arrive step2:" + Thread.currentThread());
                phaser.arriveAndAwaitAdvance();
                work2();
                phaser.arriveAndDeregister();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        private void work0() {
            System.out.println("doSometing together0");
        }
        private void work1() {
            System.out.println("doSometing together1");
        }
        private void work2() {
            System.out.println("doSometing together2");
        }
    }

    private static class Task2 implements Runnable{
        private Phaser phaser;
        public Task2(Phaser phaser) {
            this.phaser = phaser;
        }

        @Override
        public void run() {
            Random random = new Random();
            int randTime = random.nextInt(3000);
            try {
                Thread.sleep(randTime);
                System.out.println("arrive step1:" + Thread.currentThread());
                phaser.arriveAndAwaitAdvance();
                work1();
                phaser.arriveAndDeregister();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        private void work1() {
            System.out.println("doSometing together1");
        }
    }
}
