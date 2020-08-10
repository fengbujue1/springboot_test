package com.zyj.springboot_test.test.java.testThread.test_thread_pool.dongnaoDemon;

import java.util.HashSet;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantLock;
/**
 * 实现自己的线程池
 */
public class MyThreadPool implements ThreadPool {
	private int coreSize;	// 核心线程数
	private int maxSize;	// 最大线程数
	private BlockingQueue<Runnable> taskQueue;	// 任务队列
	private HashSet<Worker> workerSet = new HashSet<Worker>();	// 工作线程
	private AtomicInteger workerCount = new AtomicInteger(0);	// 当前工作线程数量
	private final ReentrantLock mainLock = new ReentrantLock();		// 可以被workerCount替代
	/**
	 * 构造函数，由调用者决定。
	 * @param cs 核心线程
	 * @param ms 最大线程数
	 * @param q 任务队列，让线程池丰富的原因
	 */
	public MyThreadPool(int cs, int ms, BlockingQueue<Runnable> q) {
		coreSize = cs;
		maxSize = ms;
		taskQueue = q;
	}
	
	/**
	 * 提交任务
	 */
	@Override
	public void execute(Runnable job) {
		// 判断工作线程数是否小于核心线程数
		if(workerCount.get() < coreSize) {
				addWorker(job);
		}else {
			if(workerCount.get() >= maxSize) {
				throw new RuntimeException("线程池繁忙，无法接受新的任务");
			}
			// 将任务放入队列
			// 放入任务队列，可能不成功，队列满了
			if(! taskQueue.offer(job) && workerCount.get() < maxSize) {
				addWorker(job);	// 队列满了，才去创建非核心线程
			}
		}
	}

	@Override
	public void shutdown() {
		mainLock.lock();
		try {
			workerSet.forEach((e)->{
				e.shutdown();	// 给出线程池即将关闭的信号
				e.thread.interrupt();	// 打断线程当前正在做的事情，比如正在队列中等待任务
			});
		}finally {
			mainLock.unlock();
		}
	}

	@Override
	public int getWaitTask() {
		return taskQueue.size();
	}
	/**
	 * 自己实现的默认的线程制作工厂
	 */
	public static final ThreadFactory defautThreadFactory = new ThreadFactory() {
		AtomicInteger sequence = new AtomicInteger(1);
		@Override
		public Thread newThread(Runnable r) {
			ThreadGroup tg = new ThreadGroup("myThreadPoolGroup");
			Thread th = new Thread(tg, r, "myThreadPool-"+sequence.getAndIncrement());
			return th;
		}
	};
	
	private boolean addWorker(Runnable r) {
		mainLock.lock();
		try {
			if(workerCount.get() < maxSize) {
				Worker w = new Worker(defautThreadFactory, r);
				workerSet.add(w);
				w.thread.start();
				workerCount.incrementAndGet();
			}
		}finally {
			mainLock.unlock();
		}
		return true;
	}
	
	class Worker implements Runnable {
		Thread thread;
		Runnable task;		// worker线程随着第一次任务的提交而创建，task表示第一任务
		volatile boolean shutdown = false;	// 关闭状态控制器
		public Worker(ThreadFactory tf, Runnable t) {
			thread = tf.newThread(this);
			task = t;
		}
		
		@Override
		public void run() {
			// 第一个，执行任务：
				// 本地第一个任务，做完以后，处于空闲状态
				// 主动找任务做，taskQueue要任务
			// 响应shutdown状态控制
			
			// 处理本地的第一个任务
			Runnable job = task;
			task = null;
			
			for(; !shutdown ;) {
				try {
					// keepAliveTime
					if(job != null || (job = taskQueue.take()) != null) {
						job.run();
						job = null;
					}
					if(job == null) {
						// 处理线程回收的事情
						// TODO 
					}
				} catch (InterruptedException e) {
					Thread.currentThread().interrupt();
					return;
				}
			}
		}
		
		public void shutdown() {
			shutdown = true;
		}
	}
}
