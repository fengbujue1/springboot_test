package com.zyj.springboot_test.test.java.testThread.test_thread_pool;

import java.util.concurrent.*;

public class Thread_pool {
    public static int threadId = 0;
    public static void main(String[] args)  {

        //阿里推荐自己使用ThreadPoolExecutor创建线程池

        //核心线程数（常规状态下，处理任务的线程数）
        int corePoolSize = 3;
        //最大线程数，（只有在工作队列满了的情况下，才会新建线程处理任务）
        //这个结果有点意思，根据结果可分析出，这种策略会出现后面的任务反而先执行
        int maximumPoolSize = 6;
        //超出最大线程数的线程的存活时间
        long keepAliveTime = 5;
        //存活的时间单位
        TimeUnit unit = TimeUnit.SECONDS;
        //工作队列，用于存放暂时无法处理的任务,可自行指定工作队列大小
        BlockingQueue<Runnable> workQueue = new LinkedBlockingDeque<>(2);
        //自定义线程工厂，可指定线程名称，分配队内存空间等操作
        ThreadFactory threadFactory = new ThreadFactory() {
            @Override
            public Thread newThread(Runnable r) {
                ThreadGroup threadGroup = Thread.currentThread().getThreadGroup();
                String groupName = threadGroup.getName();
                System.out.println("开始创建线程，线程组名字：" + groupName);

                String threadName = "thread"+ ++threadId;
                Thread thread = new Thread(threadGroup, r, threadName, 0);
                return thread;
            }
        };

        //在任务队列工作队列都满了的情况下，还有线程加入时的拒绝策略，Executors中 newCachedThreadPool方法是直接抛出异常
        RejectedExecutionHandler handler = new RejectedExecutionHandler() {
            @Override
            public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
                Run1 r1 = (Run1) r;
                System.out.println("队列满了" + r1.num + "任务将会被丢弃");
            }
        };

        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, threadFactory,handler);
        for (int i = 1; i < 11; i++) {
            threadPoolExecutor.execute(new Run1(i));
        }
        threadPoolExecutor.shutdown();
        try {
            threadPoolExecutor.awaitTermination(10, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("线程被关闭了");
    }

    static class Run1 implements Runnable {
        public int num;
        public Run1(int i) {
            num = i;
        }

        @Override
        public void run() {
            System.out.println("任务" + num + "执行");

            try {
                Thread.sleep(200000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
