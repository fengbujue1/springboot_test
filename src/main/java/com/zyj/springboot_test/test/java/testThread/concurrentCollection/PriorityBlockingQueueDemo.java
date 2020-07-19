package com.zyj.springboot_test.test.java.testThread.concurrentCollection;

import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.LockSupport;

/**
 * 不按先进先出顺序的队列
 * 
 *
 */
public class PriorityBlockingQueueDemo {
	public static void main(String[] args) {
		PriorityBlockingQueueDemo demo = new PriorityBlockingQueueDemo();
		//demo.test1();
		//demo.chooseFriend();
		demo.baiduNetdisk();
		
	}
	
	public void test1(){
		PriorityBlockingQueue<String> queue = new PriorityBlockingQueue<>(3);
        queue.put("48");
        queue.put("01");
        queue.put("12");
        queue.put("27");
        queue.put("31");

        for (;queue.size()>0;){
            try {
                System.out.println(queue.take());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
	
	public void chooseFriend() {
        // 按照指定的排序方式进行排列男友的优先级
		PriorityBlockingQueue<BoyFriend> queue = 
				new PriorityBlockingQueue<BoyFriend>(5, (BoyFriend o1, BoyFriend o2) -> {
                    	// 根据总资产，综合排序，越有钱的排在越前面
                    	int num1 = o1.houseProperty + o1.carPrice;
                        int num2 = o2.houseProperty + o2.carPrice;
                        if(o1.city < o2.city) {
                        	return 1;
                        }else if(o1.city > o2.city) {
                        	return -1;
                        } else {
                        	if (num1 > num2)
                        		return -1;
                        	else if (num1 == num2)
                        		return 0;
                        	else
                        		return 1;
                        }
                });
		
        queue.put(new BoyFriend(1, 80, 200000, "john"));
        queue.put(new BoyFriend(2, 120, 100000, "emily"));
        queue.put(new BoyFriend(3, 200, 0, "tom"));

        for (;queue.size()>0;){
            try {
                System.out.println(queue.take().name);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
	
	/**
	 * 线程池实现，优先级队列模拟百度网盘下载任务
	 */
	public void baiduNetdisk() {
		// 模拟百度网盘上传下载任务
		// 线程池中的runnalbe接口，无法通过Comparator，但是可以通过默认的Comparable接口来指定优先等级
		ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(1, 1, 60L, TimeUnit.SECONDS, 
						new PriorityBlockingQueue<Runnable>() );
		
		new Thread(()->{
			long s = System.currentTimeMillis();
			for(;System.currentTimeMillis() - s < 1000;) {
				// 下载太慢了？你的问题，充值可以解决。
				Task task = new Task(3, ()->{
					System.out.println("task");
				});
				threadPoolExecutor.execute(task);
			}
			
		}).start();
		
		new Thread(()->{
			long s = System.currentTimeMillis();
			for(;System.currentTimeMillis() - s < 1000;) {
				Task vipTask = new Task(2, ()->{
					System.out.println("vip task");
				});
				threadPoolExecutor.execute(vipTask);
			}
			
		}).start();
		
		new Thread(()->{
			long s = System.currentTimeMillis();
			for(;System.currentTimeMillis() - s < 1000;) {
				Task svipTask = new Task(1, ()->{
					System.out.println("svip task");
				});
				threadPoolExecutor.execute(svipTask);
			}
		}).start();
		
	}
	
	static class Task implements Runnable, Comparable<Task> {
		private final static AtomicInteger number = new AtomicInteger();
		private int priority;
		private Runnable job;
		private int sequence;
		public Task(int priority, Runnable job) {
			this.priority = priority;
			this.job = job;
			this.sequence = number.incrementAndGet();
		}

		@Override
		public void run() {
			job.run();
			System.out.println("priority: "+priority+" sequence: "+sequence);
			System.out.println();
			// 为更好的观赏输出效果，停留1秒
			LockSupport.parkNanos(TimeUnit.SECONDS.toNanos(1));
		}
		
		@Override
		public int compareTo(Task o) {
			// 
			int num1 = this.priority;
			int num2 = o.priority;
			if (num1 > num2)
                return 1;
            else if (num1 == num2)
                return this.sequence > o.sequence ? 1 : -1;
            else
                return -1;
		}
	}
}

class BoyFriend{
    public int city;	// 几线城市
    public int houseProperty;	// 房产面积
    public int carPrice;	// 车子
    public String name;		// 名字
    
	public BoyFriend(int city, int houseProperty, int carPrice, String name) {
		this.city = city;
		this.houseProperty = houseProperty;
		this.carPrice = carPrice;
		this.name = name;
	}
}
