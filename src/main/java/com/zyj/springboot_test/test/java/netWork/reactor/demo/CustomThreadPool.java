package com.zyj.springboot_test.test.java.netWork.reactor.demo;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 自定义自己的线程池
 *
 */
public abstract class CustomThreadPool {
	// CPU 核心数
	private static final int NCPU = Runtime.getRuntime().availableProcessors();
	
    public static ExecutorService buildPool() {
    	// 相关的配置可以通过配置参数来给与，这里写死
    	ExecutorService pool = new 
        		ThreadPoolExecutor(NCPU, (NCPU * 2), 60L, TimeUnit.SECONDS,
                new LinkedBlockingQueue<Runnable>(500), new DefaultThreadFactory());
    	return pool;
    }
    
	static class DefaultThreadFactory implements ThreadFactory {
        private static final AtomicInteger poolNumber = new AtomicInteger(1);
        private final ThreadGroup group;
        private final AtomicInteger threadNumber = new AtomicInteger(1);
        private final String namePrefix;

        DefaultThreadFactory() {
            SecurityManager s = System.getSecurityManager();
            group = (s != null) ? s.getThreadGroup() :
                                  Thread.currentThread().getThreadGroup();
            namePrefix = "reactorServerPool-" +
                          poolNumber.getAndIncrement() +
                         "-thread-";
        }

        public Thread newThread(Runnable r) {
            Thread t = new Thread(group, r,
                                  namePrefix + threadNumber.getAndIncrement(),
                                  0);
            if (t.isDaemon())
                t.setDaemon(false);
            if (t.getPriority() != Thread.NORM_PRIORITY)
                t.setPriority(Thread.NORM_PRIORITY);
            return t;
        }
    }
}
