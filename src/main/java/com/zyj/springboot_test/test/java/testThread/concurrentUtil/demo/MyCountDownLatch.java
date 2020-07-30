package com.zyj.springboot_test.test.java.testThread.concurrentUtil.demo;

import java.util.concurrent.locks.AbstractQueuedSynchronizer;

/**
 * 
 * 获取了count次锁，相当于调用了count次lock.lock();
 * 
 * CountDownLatch的本质就是一个共享锁。
 * 只不过一个已经被获取了count次数的锁，倒减回去count次数锁就能解锁，线程就能执行。
 *
 */
public class MyCountDownLatch {

    //CountDownLatch也可以看做一个共享锁
    //这个共享锁被获取了n次，n为倒计数初始值
    //只有在被释放后，才能获取锁
    Sync sync;
    MyCountDownLatch(int count){
        this.sync = new Sync(count);
    }

    public void countDown(){
        this.sync.releaseShared(1);
    }

    public void await(){
        this.sync.acquireShared(1);
    }

    static class Sync extends AbstractQueuedSynchronizer{
        /***/
		private static final long serialVersionUID = 1L;

		public Sync(int count){
            setState(count);
        }

        //相当于await方法，只有count=0 的时候，才会成功
        @Override
        protected int tryAcquireShared(int arg) {
            return getState() == 0 ? 1 : -1;
        }

        //相当于countDown
        @Override
        protected boolean tryReleaseShared(int arg) {
            for (;;){
                int c = getState();
                if (c == 0){
                    return true;
                }
                int nextc = c -1;
                if (compareAndSetState(c ,nextc)){
                    return nextc == 0;
                }
            }
        }
    }
}


