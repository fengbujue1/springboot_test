package com.zyj.springboot_test.test.java.testThread.test_atomicity_in_thread;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.*;
import java.util.function.BinaryOperator;

public class TestAtomic {
    public static void main(String[] args) {
//        testAtomicInteger();
//        testAtomicIntegerArray();
//        testAtomicReference();
//        testAtomicUpdater();
        testAddUtil();
    }
    public static void testAtomicInteger() {
        AtomicInteger atomicInteger = new AtomicInteger(1);
        System.out.println(atomicInteger.incrementAndGet());
        System.out.println(atomicInteger.compareAndSet(2, 20));
        System.out.println(atomicInteger.decrementAndGet());
        System.out.println(atomicInteger.getAndAdd(1));
        System.out.println(atomicInteger.get());

        //线程池中 线程数控制的使用示例
        int maxNum = 10;
        AtomicInteger currentNum = new AtomicInteger(9);

        if (currentNum.get() < maxNum) {//1.数量判定 避免大多数线程进入后续逻辑
            if (currentNum.incrementAndGet() <= maxNum) { //2.原子操作递增，只能有小于maxNum的线程能成功
                //返回连接
            } else {
                currentNum.decrementAndGet();//漏出来的线程需要自行递减，也是无锁，1.中的操作为了避免大多数线程都走到这一步抢锁
            }
        }
    }
    public static void testAtomicIntegerArray() {//原子更新数组
        AtomicIntegerArray atomicIntegerArray = new AtomicIntegerArray(10);
        atomicIntegerArray.compareAndSet(0, 0, 1);
        System.out.println(atomicIntegerArray.get(0));

    }

    public static void testAtomicReference() {//原子更新引用类型

        AtomicReference<Integer> objectAtomicReference = new AtomicReference<>(10);
        objectAtomicReference.accumulateAndGet(1, new BinaryOperator<Integer>() {
            @Override
            public Integer apply(Integer integer, Integer integer2) {
                return integer-integer2;
            }
        });
        System.out.println(objectAtomicReference.get());
        objectAtomicReference.compareAndSet(9, 20);
        System.out.println(objectAtomicReference.get());

        Obj obj1 = new Obj(1);
        Obj obj2 = new Obj(2);
        AtomicReference<Obj> objAtomicReference1 = new AtomicReference<>(obj1);
        System.out.println(objAtomicReference1.get().state);
        objAtomicReference1.compareAndSet(obj1, obj2);
        System.out.println(objAtomicReference1.get().state);
    }
     static class Obj{
        public volatile long state;

        private AtomicLongFieldUpdater<Obj> updater;

        public Obj(long state) {
            this.state = state;
            updater = AtomicLongFieldUpdater.newUpdater(Obj.class, "state");
        }
        public long addAndGet(long delta) {

            return updater.addAndGet(this, delta);
        }
    }

    public static void testAtomicUpdater() {//原子更新字段
        AtomicLongFieldUpdater<Obj> state = AtomicLongFieldUpdater.newUpdater(Obj.class, "state");
        Obj obj = new Obj(1);
        obj.addAndGet( 10);
        System.out.println(obj.state);

        System.out.println(state.addAndGet(obj, 11));//私有属性无法在对象外部更新
    }

    public static void testAddUtil() {//大量线程求和统计场景
        //LongAccumulator和LongAdder基于Striped64的cell单元格来实现单元格填充，一定程度上避免了 cas锁竞争--速度更快（空间换时间）


        System.out.println("计算器-------------------");
        LongAccumulator longAccumulator = new LongAccumulator((x,y)->{//计算器
            return x + y;
        },0L);
        longAccumulator.accumulate(1);
        System.out.println(longAccumulator.get());

        System.out.println("累加器-------------------");
        LongAdder longAdder = new LongAdder();
        longAdder.add(1);
        System.out.println(longAdder.sum());


        //AtomicLong只有一个空间做cas,cas频繁，时间换空间
        System.out.println("原子基本类型的自增-------------------");
        AtomicLong atomicLong = new AtomicLong(0);
        System.out.println(atomicLong.incrementAndGet());

        System.out.println("性能比较：(必须在并发情况下才能看出差距)");
        CountDownLatch countDownLatch = new CountDownLatch(3);

        new Thread(new Runnable() {
            @Override
            public void run() {
                long startTime1 = System.currentTimeMillis();
                for (int i = 0; i < 3; i++) {
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            while (System.currentTimeMillis() - startTime1 < 2000) {
                                longAdder.add(1);
                            }
                        }
                    }).start();
                }
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                countDownLatch.countDown();
            }
        }).start();
        new Thread(new Runnable() {
            @Override
            public void run() {
                long startTime2 = System.currentTimeMillis();
                for (int i = 0; i < 3; i++) {
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            while (System.currentTimeMillis() - startTime2 < 2000) {
                                longAccumulator.accumulate(1);
                            }
                        }
                    }).start();
                }
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                countDownLatch.countDown();
            }
        }).start();
        new Thread(new Runnable() {
            @Override
            public void run() {
                long startTime3 = System.currentTimeMillis();
                for (int i = 0; i < 3; i++) {
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            while (System.currentTimeMillis() - startTime3 < 2000) {
                                atomicLong.incrementAndGet();
                            }
                        }
                    }).start();
                }
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                countDownLatch.countDown();
            }
        }).start();
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("longAdder:  ");
        System.out.println(longAdder.sum());
        System.out.println("longAccumulator:" );
        System.out.println(longAccumulator.get());
        System.out.println("atomicLong: ");
        System.out.println(atomicLong.get());
    }
}
