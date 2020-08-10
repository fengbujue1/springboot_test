package com.zyj.springboot_test.test.java.testThread.concurrentUtil.demo;

import java.util.concurrent.locks.AbstractQueuedSynchronizer;

/**
 * Semaphore也是一个共享锁，形象的说是一个锁池。<br/>
 * 池中有指定数量的锁（令漏牌算法），通过用完归还的形式来循环使用。
 *
 */
public class MySemaphore {

    private Sync sync;
    
    public MySemaphore(int count){
        this.sync = new Sync(count);
    }

    public void acquire(){
        sync.acquireShared(1);
    }

    public void release(){
        sync.releaseShared(1);
    }

    //信号量，就是一个共享锁，
    //这个共享锁同时只能被n个人获取，n为信号量数量
    static class Sync extends AbstractQueuedSynchronizer{
        /** */
		private static final long serialVersionUID = 1L;
		
		public Sync(int permits) {
			setState(permits);
		}

		@Override
        protected int tryAcquireShared(int arg) {
			// 借一把锁
            for (;;){
                int state = getState();
                int nextSatte = state - arg;
                if (nextSatte >= 0){
                    if(compareAndSetState(state, nextSatte)) {
                    	return 1;
                    }
                }else{
                	// 借锁不成功，进入队列等待
                    return -1;
                }
            }
        }


        @Override
        protected boolean tryReleaseShared(int arg) {
        	// 还回来一把锁
            for (;;){
                int state = getState();
                int next = state + arg;
                if (next < state) // 防止负数操作
                    throw new Error("Maximum permit count exceeded");
                if (compareAndSetState(state, next)){
                    return true;
                }else{
                    return false;
                }
            }
        }
    }
}
