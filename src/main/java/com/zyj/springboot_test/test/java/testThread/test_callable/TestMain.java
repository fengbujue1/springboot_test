package com.zyj.springboot_test.test.java.testThread.test_callable;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

public class TestMain {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        CallableTask task = new CallableTask();

        MyFutureTask myFutureTask = new MyFutureTask(task);//给futureTask实例一个Callable实例
        System.out.println("开启任务");
        new Thread(myFutureTask).start();//还是需要通过Thread开启线程
        System.out.println("获取结果");
        String mysResult = (String) myFutureTask.getResult();
        System.out.println("结果为：" + mysResult);

        FutureTask futureTask = new FutureTask<>(task);
        new Thread(futureTask).start();
        String result = (String) futureTask.get();
        System.out.println(result);

    }
}
