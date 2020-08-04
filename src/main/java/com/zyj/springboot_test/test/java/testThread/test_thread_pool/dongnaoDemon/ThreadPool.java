package com.zyj.springboot_test.test.java.testThread.test_thread_pool.dongnaoDemon;

/**
 *一个非常简单的线程池
 *
 */
public interface ThreadPool {
	// 提交任务给线程池
	void execute(Runnable job);
	
	void shutdown();	// 关闭线程池
	
	int getWaitTask();	// 当前还有多少的任务在等待中
}
