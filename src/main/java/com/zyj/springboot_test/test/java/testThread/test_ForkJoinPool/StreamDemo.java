package com.zyj.springboot_test.test.java.testThread.test_ForkJoinPool;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinPool.ForkJoinWorkerThreadFactory;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;
import java.util.stream.Collectors;

public class StreamDemo {
	public static void main(String[] args) {
		StreamDemo demo = new StreamDemo();
		demo.produce();
		demo.compute();
		demo.compute1();
		//demo.produceData();
		/*
		demo.compute2();
		demo.compute3();
		demo.compute4();
		*/
	}
	
	List<Persion> persions = new CopyOnWriteArrayList<Persion>();
	final int persionCount = 500 * 10000;
	/**
	 * 通过cpu构建persionCount指定的数据量
	 */
	public void produce() {
		int cpu = Runtime.getRuntime().availableProcessors();
		int produceUnit = persionCount / cpu;
		CountDownLatch latch = new CountDownLatch(cpu);
		long s = System.currentTimeMillis();
		for(int c = 0; c < cpu; c++) {
			new Thread(()->{
				Random random = new Random();
				List<Persion> pl = new ArrayList<Persion>(); 
				for(int i = 0; i < produceUnit; i++) {
					char sex = random.nextInt(1) == 1 ? '1':'0';
					int age = random.nextInt(30);
					boolean m = random.nextInt(1) == 1 ? true : false;
					String name = Thread.currentThread().getName();
					name += sex == '1' ? "boy" : "gril";
					Persion p = new Persion(sex, age, m, name+"-"+i);
					pl.add(p);
				}
				persions.addAll(pl);
				latch.countDown();
			}).start();
		}
		try {
			latch.await();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println(cpu+" cpus cost time: "+(System.currentTimeMillis() -s )+" 数据准备完毕！ ");
	}
	
	public void produceData() {
		int cpu = Runtime.getRuntime().availableProcessors();
		ForkJoinPool pool = new ForkJoinPool(cpu, ForkJoinPool.defaultForkJoinWorkerThreadFactory, (t,e)->{
			System.out.println(t.getName());
			e.printStackTrace();
		}, false);
		
		ProduceTask task = new ProduceTask(10 * 10000, 10000);
		long s = System.currentTimeMillis();
		ForkJoinTask<List<Persion>> rt = pool.submit(task);
		try {
			List<Persion> pl = rt.get();
			System.out.println("cost time: "+(System.currentTimeMillis() -s )+" data size: "+pl.size());
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		} catch (ExecutionException e1) {
			e1.printStackTrace();
		}
		
	}
	
	class ProduceTask extends RecursiveTask<List<Persion>> {
		/** */
		private static final long serialVersionUID = 1L;
		int number, unit;
		public ProduceTask(int n, int u) {
			number = n;
			unit = u;
		}
		@Override
		protected List<Persion> compute() {
			boolean canCompute = (number / unit) <= 1;
			if(canCompute) {
				Random random = new Random();
				List<Persion> pl = new ArrayList<Persion>(); 
				for(int i = 0; i < unit; i++) {
					char sex = random.nextInt(1) == 1 ? '1':'0';
					int age = random.nextInt(30);
					boolean m = random.nextInt(1) == 1 ? true : false;
					String name = Thread.currentThread().getName();
					name += sex == '1' ? "boy" : "gril";
					Persion p = new Persion(sex, age, m, name+"-"+i);
					pl.add(p);
				}
				return pl;
			}else {
				int middle = number - unit;
				ProduceTask lt = new ProduceTask(unit, unit);
				ProduceTask rt = new ProduceTask(middle, unit);
				lt.fork();
				rt.fork();
				
				List<Persion> ll = lt.join();
				List<Persion> rl = rt.join();
				ll.addAll(rl);
				rl.clear();
				return ll;
			}
		}
		
	}
	
	// 标记线程是否被打印过
	ThreadLocal<Boolean> printFlag = ThreadLocal.withInitial(()->false);
	private void printThreadInfo() {
		if(!printFlag.get()) {
			System.out.println(Thread.currentThread().getName());
			printFlag.set(true);
		}
	}
	private boolean isUnMarriedGril(Persion x) {
		printThreadInfo();
		return  x.isUnMarried() && x.isGril() && x.age >= 18 && x.age <= 25;
	}
	
	public void compute() {
		long s = System.currentTimeMillis();
		int r = persions.stream().mapToInt((x)->{
			if(isUnMarriedGril(x)) {
				return 1;
			}else {
				return 0;
			}
		}).sum();
		System.out.println("cost time: "+(System.currentTimeMillis() -s));
		System.out.println((persionCount / 10000)+"万人中，18-25岁间未婚女孩有 "+r+" 个");
	}
	
	public void compute1() {
		long s = System.currentTimeMillis();
		int r = persions.parallelStream().mapToInt((x)->{
			if(isUnMarriedGril(x)) {
				return 1;
			}else {
				return 0;
			}
		}).sum();
		System.out.println("cost time: "+(System.currentTimeMillis() -s));
		System.out.println((persionCount / 10000)+"万人中，18-25岁间未婚女孩有 "+r+" 个");
	}
	
	public void compute2() {
		long s = System.currentTimeMillis();
		int r = persions.parallelStream().map((x)->{
			if(isUnMarriedGril(x)) {
				return 1;
			}else {
				return 0;
			}
		}).reduce(0,(a,b)->a+b);
		System.out.println("cost time: "+(System.currentTimeMillis() -s));
		System.out.println((persionCount / 10000)+"万人中，18-25岁间未婚女孩有 "+r+" 个");
	}
	
	public void compute3() {
		long s = System.currentTimeMillis();
		long r = persions.parallelStream().
				filter(p->isUnMarriedGril(p))
				.collect(Collectors.counting());
		System.out.println("cost time: "+(System.currentTimeMillis() -s));
		System.out.println((persionCount / 10000)+"万人中，18-25岁间未婚女孩有 "+r+" 个");
	}
	
	public void compute4() {
		long s = System.currentTimeMillis();
		int r = persions.stream().
						map(p->isUnMarriedGril(p)?1:0)
						.reduce(0,(a,b)->a+b);
		System.out.println("cost time: "+(System.currentTimeMillis() -s));
		System.out.println((persionCount / 10000)+"万人中，18-25岁间未婚女孩有 "+r+" 个");
	}
	
	class Persion {
		char sex;
		int age;
		boolean married;
		String name;
		public Persion(char sex, int age, boolean married, String name) {
			this.sex = sex;
			this.age = age;
			this.married = married;
			this.name = name;
		}
		public boolean isUnMarried() {return !married;}
		public boolean isGril() {return sex == '0';}
	}
}
