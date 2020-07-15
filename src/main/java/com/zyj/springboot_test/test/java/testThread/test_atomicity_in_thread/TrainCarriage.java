package com.zyj.springboot_test.test.java.testThread.test_atomicity_in_thread;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicIntegerArray;
import java.util.concurrent.locks.LockSupport;

/**
 * 列车到站，乘客上下车厢。<br/>
 * 还可以用于锁细化，批量产生capacity个锁，多个人同时修改，Excel的行单元格。<br/>
 * 获得锁：carriage.compareAndSet(i, 0, 1);<br/>
 * 释放锁：carriage.compareAndSet(i, 1, 0);
 *
 */
public class TrainCarriage {
	// 车厢节数
	private int capacity = 15;
	// 列车每节车厢上的乘客数量，用一个数组表示
	AtomicIntegerArray carriage = new AtomicIntegerArray(capacity);
	
	public static void main(String[] args) {
		TrainCarriage tc = new TrainCarriage();
		tc.totalPassengers();
		// 这里有一个任务，就是一辆要开往春天的火车，要经过5个站，每个站都有不同的旅客上下车
		// phaser实现，作为作业等大家去模拟。
		for(int i =0; i < 5; i++) {
			tc.upAndDown();
			tc.totalPassengers();
			LockSupport.parkNanos(tc, 1000 * 1000 * 3);
		}
	}
	
	/**
	 * 到站，乘客上下车
	 */
	public void upAndDown() {
		CountDownLatch latch = new CountDownLatch(capacity);
		System.out.println(">>>>>>>>>>>>>>>>>>>>>>列车到站");
		for(int i = 0; i < capacity; i++) {
			// 每节车厢的运作
			new Thread(new CarrWork(i, latch)).start();
		}
		try {
			latch.await();
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
	}
	
	public int totalPassengers() {
		int sum = 0;
		for(int i = 0; i < capacity; i++) {
			sum += carriage.get(i);
		}
		System.out.println(carriage.toString());
		System.out.println("===================列车总共 "+sum+" 人================");
		return sum;
	}
	
	class CarrWork implements Runnable {
		int index = 0;
		CountDownLatch latch;
		public CarrWork(int c, CountDownLatch l) {
			index = c;
			latch = l;
		}
		@Override
		public void run() {
			// 乘客上车厢
			int upPassengers = ThreadLocalRandom.current().nextInt(30);
			for(int p = 0; p < upPassengers; p ++) {
				// 代表每个乘客
				new Thread(()->{
					// activeSize < maxSize
					if(carriage.get(index) < 30) {
						if(carriage.incrementAndGet(index) <= 30) {
							System.out.println(Thread.currentThread().getName()+" 已经上车");
						}else {
							carriage.decrementAndGet(index);
							System.out.println(Thread.currentThread().getName()+" 超载，下车");
						}
					}
				}, "upPassenger-"+index+"-"+p).start();
			}
			//System.out.println("上车后情况："+carriage.toString());
			
			// 乘客下车厢
			int bound = 0;
			int downPassengers = ( bound = carriage.get(index)) <= 0 ? 0 : ThreadLocalRandom.current().nextInt(bound);
			for(int p = 0; p < downPassengers; p ++) {
				new Thread(()->{
					if(carriage.get(index) > 0) {
						if(carriage.decrementAndGet(index) >= 0) {
							System.out.println(Thread.currentThread().getName()+" 已经下车");
						}else {
							carriage.incrementAndGet(index);
							System.out.println(Thread.currentThread().getName()+" 没有乘客啦");
						}
					}
				}, "downPassenger-"+index+"-"+p).start();
			}
			//System.out.println("下车后情况："+carriage.toString());
			latch.countDown();
		}
		
	}
}
