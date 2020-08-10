package com.zyj.springboot_test.test.java.testThread.concurrentCollection;

import java.util.AbstractQueue;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.Delayed;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.LockSupport;

/**
 * 一个有特色的队列。JDK 在schedule、Timer中使用。
 * 
 *
 */
public class DelayQueueDemo {
	
	public static void main(String[] args) {
		DelayQueueDemo demo = new DelayQueueDemo();
		//demo.test();
		demo.scheduleThreadPool();
	}
	
	public void test() {
		DelayQueue<DelayJob> jobDelayQue = new DelayQueue<DelayJob>();
		jobDelayQue.add(new DelayJob((System.currentTimeMillis() + 5000), ()->{System.out.println("5");}));
		jobDelayQue.add(new DelayJob((System.currentTimeMillis() + 15000), ()->{System.out.println("15");}));
		jobDelayQue.add(new DelayJob((System.currentTimeMillis() + 25000), ()->{System.out.println("25");}));
		
		new Thread(()->{
			for(;jobDelayQue.size() > 0;) {
				DelayJob j = jobDelayQue.poll();
				if(j != null) {
					j.run();
				}else {
					System.out.println("null");
				}
				LockSupport.parkNanos(TimeUnit.SECONDS.toNanos(1));
			}
		}).start();
	}
	
	private static final AtomicInteger counter = new AtomicInteger(0);
	
	class DelayJob implements Delayed, Runnable {
	    
		private long time;	// 毫秒
		private long sequence;
		private Runnable task;
		
		public DelayJob(long delay, Runnable task) {
			this.sequence = counter.incrementAndGet();
			this.time = delay;
			this.task = task;
		}
		
		@Override
		public int compareTo(Delayed o) {
			if(o == this) return 0;
			if(o instanceof DelayJob) {
				DelayJob other = (DelayJob) o;
				long diff = time - other.time;
				if(diff < 0) return -1;
				else if(diff > 0) return 1;
				else if(sequence < other.sequence) return -1;
				else return 1;
			}
			long d = getDelay(TimeUnit.NANOSECONDS) - o.getDelay(TimeUnit.NANOSECONDS);
			return (d == 0) ? 0 : (d < 0) ? -1 : 1;
		}

		@Override
		public long getDelay(TimeUnit unit) {
			long diff = time - System.currentTimeMillis();
			return unit.convert(diff, TimeUnit.MILLISECONDS);
		}

		@Override
		public void run() {
			task.run();
			System.out.println(new Date(time));
		}
	}
	
	public void scheduleThreadPool() {
		DelayWorkQueue jobDelayQue = new DelayWorkQueue();
		/* 第一步演示
		jobDelayQue.add(new DelayJob((System.currentTimeMillis() + 5000), ()->{System.out.println("5");}));
		jobDelayQue.add(new DelayJob((System.currentTimeMillis() + 15000), ()->{System.out.println("15");}));
		jobDelayQue.add(new DelayJob((System.currentTimeMillis() + 25000), ()->{System.out.println("25");}));
		
		new Thread(()->{
			for(;jobDelayQue.size() > 0;) {
				Runnable j = jobDelayQue.poll();
				if(j != null) {
					j.run();
				}else {
					System.out.println("null");
				}
				LockSupport.parkNanos(TimeUnit.SECONDS.toNanos(1));
			}
		}).start();
		*/
		
		/* 为何这样不能达到延时效果？*/
		/* 线程池的执行逻辑：
		 * 1. 首先找空闲的核心线程，直接执行 
		 * 2. 如果没有空闲的，放入任务队列, 
		 * 3. 任务队列满了后才根据maxSize创建新的线程
		 * */
		/*
		 ThreadPoolExecutor threadPoolExecutor = 
				new ThreadPoolExecutor(1, 1, 0, TimeUnit.MILLISECONDS, 
						new DelayWorkQueue());
		threadPoolExecutor.execute(new DelayJob((System.currentTimeMillis() + 5000), ()->{System.out.println("5");}));
		 /* */
		/* 可以这样做，但是不能保证稳定*/
		/*
		 ThreadPoolExecutor threadPoolExecutor = 
				new ThreadPoolExecutor(0, 1, 0, TimeUnit.MILLISECONDS, 
						jobDelayQue);
		 threadPoolExecutor.execute(new DelayJob((System.currentTimeMillis() + 5000), ()->{System.out.println("5");}));
		 // 不能保证稳定：因为前面的execute已经激活了coreSize，任务执行完，处于空闲状态。
		 threadPoolExecutor.execute(new DelayJob((System.currentTimeMillis() + 5000), ()->{System.out.println("5");}));
		 /* */
		// 稳定的做法
		
		//Executors.newScheduledThreadPool(2);
		/* ScheduledThreadPool的execut逻辑：
		 * 1. 先将任务放入延时队列，
		 * 2. 查看工作线程池是否运行，未运行则启动它
		 * */
		// 模拟，ScheduledThreadPool逻辑确保线程池被启动，先
		
		ThreadPoolExecutor threadPoolExecutor = 
				new ThreadPoolExecutor(1, 1, 0, TimeUnit.MILLISECONDS, 
						jobDelayQue);
		threadPoolExecutor.execute(()->{
			System.out.println("延时任务线程池启动了");
		});
		
		new Thread(()->{
			for(int i = 10; i > 0; i=i-2) {
				DelayJob task = new DelayJob((System.currentTimeMillis() + (i * 1000)), 
						()->{System.out.println();});
				jobDelayQue.offer(task);
			}
		}).start();
		
		new Thread(()->{
			for(int i = 1; i < 10; i=i+2) {
				DelayJob task = new DelayJob((System.currentTimeMillis() + (i * 1000)), 
						()->{System.out.println();});
				jobDelayQue.offer(task);
			}
		}).start();
		
	}
	
	class DelayWorkQueue extends AbstractQueue<Runnable> implements BlockingQueue<Runnable> {
		DelayQueue<DelayJob> delayQueue;
		
		public DelayWorkQueue() {
			delayQueue = new DelayQueue<DelayJob>();
		}
		
		@Override
		public Runnable poll() {
			return delayQueue.poll();
		}

		@Override
		public Runnable peek() {
			return delayQueue.peek();
		}

		@Override
		public boolean offer(Runnable e) {
			DelayJob t = (DelayJob) e;
			return delayQueue.offer(t);
		}

		@Override
		public void put(Runnable e) throws InterruptedException {
			DelayJob t = (DelayJob) e;
			delayQueue.put(t);
		}

		@Override
		public boolean offer(Runnable e, long timeout, TimeUnit unit) throws InterruptedException {
			DelayJob t = (DelayJob) e;
			return delayQueue.offer(t, timeout, unit);
		}

		@Override
		public Runnable take() throws InterruptedException {
			return delayQueue.take();
		}

		@Override
		public Runnable poll(long timeout, TimeUnit unit) throws InterruptedException {
			return delayQueue.poll(timeout, unit);
		}

		@Override
		public int remainingCapacity() {
			return delayQueue.remainingCapacity();
		}

		@Override
		public int drainTo(Collection<? super Runnable> c) {
			return delayQueue.drainTo(c);
		}

		@Override
		public int drainTo(Collection<? super Runnable> c, int maxElements) {
			return delayQueue.drainTo(c, maxElements);
		}

		@Override
		public Iterator<Runnable> iterator() {
			Iterator<DelayJob> iter = delayQueue.iterator();
			List<Runnable> res = new ArrayList<Runnable>();
			while(iter.hasNext()) {
				res.add(iter.next());
			}
			return res.iterator();
		}

		@Override
		public int size() {
			return delayQueue.size();
		}
		
	}
}


