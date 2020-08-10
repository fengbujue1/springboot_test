package com.zyj.springboot_test.test.java.testThread.concurrentCollection.map.TreeMapUsage;

import java.util.List;
import java.util.Random;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

public class ConcurrentSkipListMapDemo {
	public static void main(String[] args) {
		ConcurrentSkipListMapDemo demo = new ConcurrentSkipListMapDemo();
		//demo.test();
		demo.findUnmarriedGril();
	}
	
	public void test() {
		// 最强大的一个Map，继承了所有Map接口的基因，大并发下线程安全的有序map
		ConcurrentSkipListMap<Integer, String> skipMap = new ConcurrentSkipListMap<Integer, String>();
		skipMap.put(1, "one");
		skipMap.put(2, "two");
		skipMap.put(3, "three");
		skipMap.put(4, "four");
		skipMap.put(5, "five");
		skipMap.put(6, "six");
		
		// 返回集合条目方法：subMap、headMap、tailMap
		System.out.println();
		System.out.println("tailMap");
		System.out.println(skipMap.tailMap(4));
		System.out.println(skipMap.tailMap(4, false));
		
		// 获得倒排序集合descendingMap
		skipMap.descendingMap();
		
		// 获得倒排序Key集合
		skipMap.descendingKeySet();
		
	}
	
	public void findUnmarriedGril() {
		ConcurrentSkipListMap<Integer, List<Persion>> skipMap = new ConcurrentSkipListMap<Integer, List<Persion>>();
		
		CountDownLatch latch = new CountDownLatch(2);
		new Thread(()->{
			Random random = new Random();
			for(int i = 0; i < 100000; i++) {
				char sex = random.nextInt(1) == 1 ? '1':'0';
				int age = random.nextInt(30);
				boolean m = random.nextInt(1) == 1 ? true : false;
				String name = sex == '1' ? "boy" : "gril";
				Persion p = new Persion(sex, age, m, name+"-"+i);
				List<Persion> ps = skipMap.get(age);
				if(ps == null) {
					ps = new CopyOnWriteArrayList<Persion>();
					skipMap.put(age, ps);
				}
				ps.add(p);
			}
			latch.countDown();
		}).start();
		
		new Thread(()->{
			Random random = new Random();
			for(int i = 0; i < 100000; i++) {
				char sex = random.nextInt(1) == 1 ? '1':'0';
				int age = random.nextInt(30);
				boolean m = random.nextInt(1) == 1 ? true : false;
				String name = sex == '1' ? "boy" : "gril";
				Persion p = new Persion(sex, age, m, name+"-"+i);
				List<Persion> ps = skipMap.get(age);
				if(ps == null) {
					ps = new CopyOnWriteArrayList<Persion>();
					skipMap.put(age, ps);
				}
				ps.add(p);
			}
			latch.countDown();
		}).start();
		
		try {
			latch.await();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		AtomicInteger t = new AtomicInteger(0);
		skipMap.subMap(18, true, 25, true).forEach((k, e)->{
			Integer totalUnMarGirl = e.stream().map((x)->{//使用了 ForkJoinPool
					if(x.isUnMarried() && x.isGril()) {
						return 1;
					}else {
						return 0;
					}
				}).reduce(0,(a,b)->a+b);
			t.getAndAdd(totalUnMarGirl);
		});
		System.out.println("20万人中，18-25岁间未婚女孩有 "+t.get()+" 个");
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
