package com.zyj.springboot_test.test.java.testThread.concurrentUtil.demo;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 线程循环栅栏。达到指定的线程数量后，线程才会一起执行，并且是可以循环使用。
 * 
 *
 */
public class MyCyclicBarrierWithLock {
	// 指定的需要等待的线程数量
	private int parties;
	// 当前正在等待的线程数
	private int curCount = 0 ;
	
    private Lock lock = new ReentrantLock();
    // 前n-1个调用await方法的线程，存放在这个等待队列
    private Condition triapCondition = lock.newCondition();
    // 换一批，下个循环开始，的标记
    private Object generation = new Object();
    
    public MyCyclicBarrierWithLock(int parties){
        this.parties = parties;
    }

    public void await(){
    	lock.lock();
    	try {
    		Object g = generation;
            curCount ++;
            if (curCount == parties){
                //人数到齐了，唤醒triapContion上的人，准备进入下一轮计数
                nextGeneration();
            }else{
            	// 人数还没到，进入等待队列，等待其他线程
                for (;;){
                    try {
                    	triapCondition.await();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    if (g != generation){
                        return;
                    }

                }
            }
    	}finally {
    		lock.unlock();
    	}
    }

    private void nextGeneration(){
    	lock.lock();
    	try {
    		triapCondition.signalAll();
    		this.curCount = 0;
            this.generation = new Object();
    	}finally {
    		lock.unlock();
    	}
    }
}
