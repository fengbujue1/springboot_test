package com.zyj.springboot_test.test.java.testThread.concurrentCollection.queue;

import java.util.PriorityQueue;
import java.util.concurrent.*;

public class TestQueue {
    public static void main(String[] args) {
        testSynchronousQueueInThreadPool();
    }
    public static void Queues() throws InterruptedException {
        /**
         * 队列：先进先出
         */
        new ConcurrentLinkedQueue<>();

        /** ArryaBlockingQueue ----数组容器*/
        ArrayBlockingQueue<Integer> integers = new ArrayBlockingQueue<>(2);//有界阻塞队列。数组实现
        System.out.println(integers.add(1));
        System.out.println(integers.add(2));
        System.out.println(integers.add(3));//add方法，队列满了后抛出异常
        System.out.println(integers.offer(1));//添加方法，满了后返回false
        try {
            integers.put(1);//添加方法，满了后阻塞
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        integers.remove();//获取方法，空队列抛出异常
        integers.poll();//获取方法，空队列返回false
        integers.take();//获取方法，空队列阻塞

        /** LinkedBlockingQueue ----链表容器*/
        LinkedBlockingQueue<Integer> integers1 = new LinkedBlockingQueue<>();//无界阻塞队列，链表实现,通过lock进行同步控制
        integers1.offer(1);
        integers1.add(1);
        integers1.put(1);

        integers1.take();


        /** PriorityBlockingQueue ------带权优先级的队列---业务场景百度网盘（根据会员等级优先处理任务）*/
        PriorityBlockingQueue<Object> objects1 = new PriorityBlockingQueue<>();

        /**DelayQueue 根据时间作为优先级的阻塞队列 */
        DelayQueue<Delayed> delayeds = new DelayQueue<>();

        /**  SynchronousQueue 容量为0的队列  生产者或者消费者 都可能陷入阻塞（非异步化） */
        SynchronousQueue<Object> objects = new SynchronousQueue<>();

        /** LinkedTransferQueue 特殊方法：queue.transfer()一定要等到消费者读到数据才会返回*/
        LinkedTransferQueue<Object> objects2 = new LinkedTransferQueue<>();
        objects2.transfer(new Object());

        /** ConcurrentLinkedQueue*/
        ConcurrentLinkedQueue<Object> objects3 = new ConcurrentLinkedQueue<>();
        boolean offer = objects3.offer("123");

        //阻塞队列在线程池中的使用

        //以下两者的任务队列使用的都是LinkedBlockingQueue,无限添加等待执行的任务
        Executors.newFixedThreadPool(1);
        Executors.newSingleThreadExecutor();

        //使用 的是SynchronousQueue,任务队列容量为空，
        // 每次有新任务都会添加失败，导致开启临时线程（ThreadPoolExecutor 1378行addwork）
        //当有线程完成工作后 读取新任务如果没有会被阻塞等待，知道有新的任务添加进来
        Executors.newCachedThreadPool();

        //定时任务线程池  使用的是延时队列
        Executors.newScheduledThreadPool(3);
    }

    /**
     * 测试 jdk线程池中 使用 SynchronousQueue（容量为空的队列）作为 任务队列的线程池，如何实现任务的存取
     */
    public static void testSynchronousQueueInThreadPool() {
        ExecutorService executorService = Executors.newCachedThreadPool();
        for (int i = 0; i < 10; i++) {
            executorService.execute(new Runnable() {
                @Override
                public void run() {
                    System.out.println(1);
                }
            });
        }
    }
}
