package com.zyj.springboot_test.test.java.testThread.concurrentCollection;

import java.util.concurrent.LinkedTransferQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;

/**
 * 
 * 
 *
 */
public class LinkedTransferQueueDemo {
	
	public static void main(String[] args) {
		LinkedTransferQueueDemo demo = new LinkedTransferQueueDemo();
		demo.test();
	}
	
	public void test() {
		LinkedTransferQueue<String> queue = new LinkedTransferQueue<String>();
		
		queue.add("1");
		queue.add("2");
		queue.add("3");
		
		new Thread(()->{
			try {
				while(true) {
					System.out.println(queue.take());
					LockSupport.parkNanos(TimeUnit.SECONDS.toNanos(3));
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}).start();
		
		new Thread(()->{
			try {
				queue.put("put");
				// 一定要等到消费者读取到值才返回
				queue.transfer("transfer");
				System.out.println("done!");
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}).start();
	}
}
