package com.zyj.springboot_test.test.java.testThread.test_thread_pool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ExecutorsInJDK {
    public static void main(String[] args) throws InterruptedException {
        //JDK提供的线程池实例化工具，简便，代价就是有许多默认参数无法自己调控，容易造成OOM
        ExecutorService executorService = Executors.newFixedThreadPool(3);
        for (int i = 1; i < 11; i++) {
            executorService.execute(new Thread_pool.Run1(i));
        }
//        executorService.shutdownNow();
        executorService.shutdown();
        executorService.awaitTermination(1, TimeUnit.SECONDS);
    }

}
