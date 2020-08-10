package com.zyj.springboot_test.test.java.testThread.test_atomicity_in_thread;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;

public class AtomicIntegerFieldUpdaterDemo {
	/*
	 // 必须state为public，破坏了面向对象的封装特性
    	private static AtomicIntegerFieldUpdater<Order> orderStateAtomicUpdater =
    			AtomicIntegerFieldUpdater.newUpdater(Order.class, "state");
    	private static AtomicIntegerFieldUpdater<Order> orderCountAtomicUpdater =
    			AtomicIntegerFieldUpdater.newUpdater(Order.class, "count");
	 */
    
    public static void main(String[] args) {
    	Order order = new Order(100, 0, "第一笔订单");
    	System.out.println(order);
        // orderStateAtomicUpdater.incrementAndGet(order);
    	order.incrementAndGetState();
        System.out.println("调用orderStateAtomicUpdater后，state值变为：" + order.getState());
        
        // 并发修改订单商品数量
        int threads = 200;
        final CountDownLatch latch = new CountDownLatch(threads);
        for(int i = 0; i < threads; i++) {
        	new Thread(()->{
        		//orderCountAtomicUpdater.incrementAndGet(order);
        		order.incrementAndGetCount();
        		latch.countDown();
        	}).start();
        }
        try {
			latch.await();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
        System.out.println(order);
    }
}

// 可以容忍多个线程修改，幂等性，cas提供了
class Order {
	// 使用AtomicIntegerFieldUpdater需要注意两个点：
	// ☆第一，因为原子更新字段类都是抽象类，每次使用的时候必须使用静态方法newUpdater()创建一个更新器，指定要更新的类和属性名
	private static AtomicIntegerFieldUpdater<Order> orderStateAtomicUpdater =
			AtomicIntegerFieldUpdater.newUpdater(Order.class, "state");
	private static AtomicIntegerFieldUpdater<Order> orderCountAtomicUpdater =
			AtomicIntegerFieldUpdater.newUpdater(Order.class, "count");
	
    private int id;
    // ☆第二，Order类的state属性必须被 volatile修饰
    // 如果Order类的state属性期望能被外界AtomicIntegerFieldUpdater修改，还必须被public修饰，这样就失去了封装的特性了
    private volatile int state; // 幂等性：操作是从0->1，发送多次操作ca(0,1)，会不会影响结果呢？
    private volatile int count;
    private String name;
    
    public int getState() {
		return orderStateAtomicUpdater.get(this);
	}
	public int getCount() {
		return orderCountAtomicUpdater.get(this);
	}
	public int incrementAndGetState() {
    	return orderStateAtomicUpdater.incrementAndGet(this);
    }
    public boolean compareAndSetState(int expect, int update) {
    	return orderStateAtomicUpdater.compareAndSet(this, expect, update);
    }
    public int incrementAndGetCount() {
    	return orderCountAtomicUpdater.incrementAndGet(this);
    }
    
    public Order(int id, int state, String name) {
        this.id = id;
        this.state = state;
        this.name = name;
    }

	@Override
	public String toString() {
		return "Order [id=" + id + ", state=" + state + ", count=" + count + ", name=" + name + "]";
	}
}

