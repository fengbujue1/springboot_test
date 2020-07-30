package com.zyj.springboot_test.test.java.testThread.concurrentUtil.demo;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

public class MyReentrantLock implements Lock {
	
	static class Sync extends AbstractQueuedSynchronizer {
		/** */
		private static final long serialVersionUID = 1L;
		protected boolean tryAcquire(int arg) {
			
			return false;
		}
		
		protected boolean tryRelease(int arg) {
			return false;
		}
		/**
		 * 是否处于占用状态
		 */
		protected boolean isHeldExclusively() {
			
			return false;
		}
	}
	
	
	@Override
	public void lock() {
		
	}

	@Override
	public void lockInterruptibly() throws InterruptedException {
		
	}

	@Override
	public boolean tryLock() {
		return false;
	}

	@Override
	public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
		return false;
	}

	@Override
	public void unlock() {
		
	}

	@Override
	public Condition newCondition() {
		return null;
	}

}
