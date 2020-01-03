package com.zyj.springboot_test.test.java.testThread.thread_base;

public class ThreadStatusTest {
    public static void main(String[] args) throws Exception{
//        test1();//线程 创建 -》运行—》结束
//        test2();//线程 创建 -》运行-》等待（带时间的）—》结束
//        test3();//线程 创建 -》运行-》阻塞（锁机制） —》结束
//        test4();


    }

    /**
     * 测试isAlive方法
     * 线程创建为 start的时候，是未存活的   返回false
     * start方法调用字后，线程没有死亡之前，线程是活着的  返回true
     * @throws InterruptedException
     */
    public static void testThreadIsAlive() throws InterruptedException {
        Thread thread = new Thread();
        System.out.println(thread.isAlive());
        thread.start();
        System.out.println(thread.isAlive());
    }
    public static void test1() throws InterruptedException {
        // 第一种状态切换 - 新建 -> 运行 -> 终止
        Thread thread1 = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("子线程发来贺电。。。");
            }
        });

        System.out.println("1、调用start方法前，thread1状态：" + thread1.getState().toString());
        Thread.sleep(2000L);
        thread1.start();
        System.out.println("2、调用start方法后，thread1状态：" + thread1.getState().toString());

        Thread.sleep(4000L); // 等待thread1执行结束，再看状态
        System.out.println("3、2s后，thread1状态：" + thread1.getState().toString());
        //thread1.start();     //TODO 注意，线程终止之后，再进行调用，会抛出IllegalThreadStateException异常


    }

    public static void test2() throws InterruptedException {
        System.out.println("############第二种：新建 -> 运行 -> 等待 -> 运行 -> 终止(sleep方式)###########################");
        Thread thread2 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {// 将线程2移动到等待状态，5s后自动唤醒
                    Thread.sleep(5000);
                    System.out.println("3、Sleep结束," + Thread.currentThread().getName() + "当前状态：" + Thread.currentThread().getState());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        thread2.start();
        System.out.println("1、调用start后，thread2状态：" + thread2.getState().toString());

        Thread.sleep(2000L); // 等待200毫秒，再看状态
        System.out.println("2、等待2s后，thread2状态：" + thread2.getState().toString());

    }

    public static void test3() throws InterruptedException {
        System.out.println("############第三种：新建 -> 运行 -> 阻塞 -> 运行 -> 终止###########################");

        //创建thread3，暂不启动
        Thread thread3 = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("2、抢锁之前，thread3状态：" + Thread.currentThread().getState().toString());
                synchronized (ThreadStatusTest.class) {
                    System.out.println("5、thread3，拿到锁，继续执行，thread3状态" + Thread.currentThread().getState());
                }
            }
        });


        synchronized (ThreadStatusTest.class) {
            System.out.println("1、主线程拿到锁，启动thread3");
            thread3.start();
            Thread.sleep(5000L);
            System.out.println("3、thread3的状态:" +  thread3.getState());
            Thread.sleep(24000L);
        }
        System.out.println("4、主线程释放锁");

    }

    public static void test4() throws InterruptedException {
        Object obj = new Object();
        Run2 run2 = new Run2(obj);
        Thread thread = new Thread(run2);
        thread.start();//先开启线程
        Thread.sleep(1000);
        synchronized (obj) {
            System.out.println("调用wait方法后的线程状态：（Waiting）" +thread.getState().toString());
            obj.notify();
        }
    }

    static class Run2 implements Runnable {
        private Object object;
        public Run2(Object obj) {
            object = obj;
        }

        @Override
        public void run() {
            synchronized (object) {
                try {
                    System.out.println("调用wait方法前的状态：（Runnable）" + Thread.currentThread().getState().toString());
                    object.wait();
                    System.out.println("被唤醒后的状态：（Runnable）" + Thread.currentThread().getState().toString());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        }
    }
}
