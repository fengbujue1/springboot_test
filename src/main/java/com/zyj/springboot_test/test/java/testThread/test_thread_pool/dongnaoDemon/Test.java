package com.zyj.springboot_test.test.java.testThread.test_thread_pool.dongnaoDemon;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;

public class Test {
	public static void main(String[] args) {
		MyThreadPool pool = new MyThreadPool(5, 10, new LinkedBlockingQueue<Runnable>(10));
		for(int i = 0; i < 21; i++) {
			pool.execute(()->{
				System.out.println(Thread.currentThread().getName()+" 在执行任务");
				LockSupport.parkNanos(TimeUnit.SECONDS.toNanos(3));
				System.out.println(pool.getWaitTask());
			});
		}
		pool.shutdown();
	}
}
