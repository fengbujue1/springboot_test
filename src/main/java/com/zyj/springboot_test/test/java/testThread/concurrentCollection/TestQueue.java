package com.zyj.springboot_test.test.java.testThread.concurrentCollection;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class TestQueue {
    public static void main(String[] args) throws InterruptedException {
        /**
         * 队列：先进先出
         */

        new ConcurrentLinkedQueue<>();

        ArrayBlockingQueue<Integer> integers = new ArrayBlockingQueue<>(2);//有界阻塞队列。数组实现
        System.out.println(integers.add(1));
        System.out.println(integers.add(2));
        System.out.println(integers.add(3));//add方法，队列满了后抛出异常
        System.out.println(integers.offer(1));//添加方法，满了后返回false
        try {
            integers.put(1);//添加方法，满了后阻塞
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        integers.remove();//获取方法，空队列抛出异常
        integers.poll();//获取方法，空队列返回false
        integers.take();//获取方法，空队列阻塞

        LinkedBlockingQueue<Integer> integers1 = new LinkedBlockingQueue<>();//无界阻塞队列，链表实现



    }
}
