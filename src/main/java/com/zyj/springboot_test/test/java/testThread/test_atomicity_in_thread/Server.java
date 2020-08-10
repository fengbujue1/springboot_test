package com.zyj.springboot_test.test.java.testThread.test_atomicity_in_thread;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicReference;

/**
 * AtomicReference代码应用示例，在高并发下能无锁化的保证
 * 1. 保证服务实例的端口不会被多次占用释放
 * 2. 保证运行流程不会变乱，将严格按照定义的状态顺序进行
 * 
 */
public class Server {
	/**定义运行状态*/
	public enum State {
		ready, running, stop;
	};
	
	private AtomicReference<State> state = new AtomicReference<State>();
	
	public Server() {
		state.set(State.ready);
	}
	
	public void start() {
		if(state.compareAndSet(State.ready, State.running)) {
			// 防止，端口已经被占用了
			System.out.println("绑定了8080端口，server started!");
		}
	}
	
	public void stop() {
		if(state.compareAndSet(State.running, State.stop)) {
			System.out.println("server stoped!");
		}
	}
	
	public static void main(String[] args) {
		Server server = new Server();
		CountDownLatch latch = new CountDownLatch(1);
		for(int i = 0; i < 10; i++) {
			Thread startTh = new Thread(()->{
				try {
					latch.await();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				server.start();
			});
			startTh.start();
			
			Thread stopTh = new Thread(()->{
				try {
					latch.await();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				server.stop();
			});
			stopTh.start();
		}
		latch.countDown();
		
		try {
			Thread.currentThread().join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
