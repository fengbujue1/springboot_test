package com.zyj.springboot_test.test.java.testThread.test_ForkJoinPool;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;

public class ForkJoinPoolDemo {

	public static void main(String[] args) {
		ForkJoinPoolDemo demo = new ForkJoinPoolDemo();
		demo.count();
	}
	
	
	public void count() {
		ForkJoinPool pool = new ForkJoinPool();
		CountTask task = new CountTask(1, 1000000);
		// 线程池的执行逻辑：1. 有没有空闲的核心线程，有丢给他执行
		ForkJoinTask<Integer> rt = pool.submit(task);
		// 如果有任务取消或抛出异常，则返回true
		if(rt.isCompletedAbnormally()) {
			rt.getException().printStackTrace();
		}
		
		try {
			// 
			System.out.println(rt.get());
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
	}
	
	public static final int threshold = 2;
	class CountTask extends RecursiveTask<Integer> {
		/** */
		private static final long serialVersionUID = 1L;
		int start, end;
		public CountTask(int s, int e) {
			start = s;
			end = e;
		}

		@Override
		protected Integer compute() {
			int sum = 0;
			// 先判断任务粒度是否能够进行执行，threshold可以自定
			boolean canCompute = (end - start) <= threshold;
			if(canCompute) {
				// 达到执行一个任务的基本拆分数，开始执行任务
				for(int i = start; i <= end; i++) {
					sum += i;
				}
			}else {
				// 任务太大，拆解任务
				int middle = (start + end) / 2;
				CountTask left = new CountTask(start, middle);
				CountTask right = new CountTask(middle+1, end);
				
				// 太极生两仪，两仪生四象，四象生八卦
				// fork拆分成左右子任务
				// 实际上类似于 pool.submit(task);
				left.fork();
				right.fork();
				
				// join等待子任务返回结果，总和子任务结果
				int lr = left.join();
				int rr = right.join();
				sum = lr + rr;
			}
			return sum;
		}
	}
}
