package com.zyj.springboot_test.test.java.testThread.test_ForkJoinPool;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;

public class TestForkJoinPool {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        TestForkJoinPool testForkJoinPool = new TestForkJoinPool();
        testForkJoinPool.useage();
    }
    public  void useage() throws ExecutionException, InterruptedException {
        ForkJoinPool forkJoinPool = new ForkJoinPool();//一个线程池：默认使用当前CPU核心数的线程数，不定义异常处理方法
        Task task = new Task(1,10000);
        ForkJoinTask<Integer> submit = forkJoinPool.submit(task);
        System.out.println(submit.get());
    }

     class Task extends RecursiveTask<Integer> {
        int start;
        int end;

        public Task(int start,int end) {
            this.start = start;
            this.end = end;
        }

        @Override
        protected Integer compute() {
            if (end-start > 10) {
                int mid = (end+start) / 2;

                Task leftTask = new Task(start,mid);
                Task rightTask = new Task(mid+1, end);
                rightTask.fork();
                leftTask.fork();

                int sum1 = rightTask.join();
                int sum2 = leftTask.join();
                return sum1 + sum2;
            } else {
                int sum = 0;
                for (int i = start; i <= end; i++) {
                    sum += i;
                }
                return sum;
            }
        }

    }

}
