package com.zyj.springboot_test.test.java.netWork.reactor;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 *   Reactor模式，无非就是将原来一个人干的事情进行的分类拆分，拆分成适合多个岗位的人干。
 *   比如连接请求处理，交给Acceptor；客户端数据读取写入处理，交给Handler；IO多路复用，交给Reactor；<br/>
 *
 *      selector完成IO多路复用事件的派发
 *
 */

public class TestMultithWorkersReactor {
    public static void main(String[] args) throws IOException {
        Reactor reactor = new Reactor(8088);
        reactor.run();

    }

}

class Reactor implements Runnable {

    final Selector selector;
    final ServerSocketChannel serverSocketChannel;
    final ThreadPoolExecutor threadPoolExecutor = createThreadPool();
    /**
     * 构造器 做一些 初始操作
     * 1.监听通道开启
     * 2.端口绑定。
     * 3.事件监听注册
     * 4.事件处理器绑定
     */
    public Reactor(int port) throws IOException{
        this.selector = Selector.open();//开启一个选择器
        this.serverSocketChannel = ServerSocketChannel.open();//开启一个服务监听通道
//        serverSocketChannel.socket().bind(new InetSocketAddress(port));
        serverSocketChannel.bind(new InetSocketAddress(port));//绑定端口
        serverSocketChannel.configureBlocking(false);//设置为非阻塞

        SelectionKey selectionKey = serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);//注册到选择器，对accpet 连接请求感兴趣

        //此处的selectionKey代表的是serverSocketChannel和 selector之间的关系
        selectionKey.attach(new Accpter(selectionKey));//绑定一个 触发了accpet事件以后的处理器

        System.out.println("reactor 构造完成");
    }

    @Override
    public void run() {//将当前堆积的事件统一取出，循环进行处理
        while (!Thread.interrupted()) {
            try {
                int num = selector.select();//阻塞等待 有新的事件统治到来
                if (num == 0) {
                    continue;
                }
                System.out.println("事件数量    " + num);
                Set<SelectionKey> selectionKeys = selector.selectedKeys();
                Iterator<SelectionKey> iterator = selectionKeys.iterator();
                while (iterator.hasNext())
                    dispatch(iterator.next());

                System.out.println("处理完一轮 事件");
                selectionKeys.clear();//将当前处理过的任务都清除掉
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    private ThreadPoolExecutor createThreadPool() {
        //核心线程数（常规状态下，处理任务的线程数）
        int corePoolSize = 3;
        //最大线程数，（只有在工作队列满了的情况下，才会新建线程处理任务）
        //这个结果有点意思，根据结果可分析出，这种策略会出现后面的任务反而先执行
        int maximumPoolSize = 6;
        //超出最大线程数的线程的存活时间
        long keepAliveTime = 5;
        //存活的时间单位
        TimeUnit unit = TimeUnit.SECONDS;
        //工作队列，用于存放暂时无法处理的任务,可自行指定工作队列大小
        BlockingQueue<Runnable> workQueue = new LinkedBlockingDeque<>();
        //自定义线程工厂，可指定线程名称，分配队内存空间等操作
        return new ThreadPoolExecutor(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue);
    }

    /**
     * 派遣分发事件任务
     */
    private void dispatch(SelectionKey selectionKey) {
        Runnable accpter = (Runnable) selectionKey.attachment();//将 这个事件上面的处理器取下来
        if (accpter != null) {
            accpter.run();//处理事件
        }
    }



    /**
     *
     * 内部类
     *
     * 用于将连接实例化，然后交由特定的 处理器处理
     */
    private class Accpter implements Runnable {
        final AtomicInteger count = new AtomicInteger(0);
        final SelectionKey selectionKey;
        final ServerSocketChannel serverSocketChannel;


        public Accpter(SelectionKey selectionKey) {
            this.selectionKey = selectionKey;
            this.serverSocketChannel = (ServerSocketChannel) selectionKey.channel();
        }

        @Override
        public void run() {
            System.out.println("阻塞等待连接建立");
            SocketChannel socketChannel = null;//建立一个和客户端的连接,阻塞
            try {
                socketChannel = serverSocketChannel.accept();
                if (socketChannel != null) {
                    System.out.println("建立一个和客户端的连接");
                    new Handler(selector, socketChannel, threadPoolExecutor);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    final class Handler implements Runnable {

        final SelectionKey selectionKey;
        final SocketChannel socketChannel;
        final ThreadPoolExecutor threadPool;

//        ByteBuffer writeBuffer = ByteBuffer.allocate(1024);//申请一段写内存
        ByteBuffer readBuffer = ByteBuffer.allocate(1024);//申请一段读内存
        static final int readstate=0, writestate = 1;//定义读写两个状态

        int state = readstate;//初始默认为读状态


        public Handler(Selector selector, SocketChannel socketChannel, ThreadPoolExecutor threadPool) throws IOException {
            this.threadPool = threadPool;
            this.socketChannel = socketChannel;
            socketChannel.configureBlocking(false);//设为非阻塞模式

            //将这个与客户端的连接注册到选择器中，
            selectionKey = socketChannel.register(selector, 0);
            selectionKey.attach(this);//同时把自己作为事件处理器

            System.out.println("绑定处理器，并唤醒等待线程");
            selectionKey.interestOps(SelectionKey.OP_READ);//对读事件感兴趣

            selector.wakeup();//唤醒select()方法上等待着的线程，告诉他他感兴趣的事发生了变化

        }

        @Override
        public void run() {
            System.out.println("开始读写");
            try {
                if (state == readstate) {
                    read();
                } else if (state == writestate) {
                    write();
                }

            } catch (IOException e) {

            }

        }


        /**
         * 写事件处理
         */
        private void write() throws IOException {
            ByteBuffer writeBuffer = ByteBuffer.allocate(1024);
            writeBuffer.put("hello world".getBytes(Charset.forName("utf8")));

            writeBuffer.flip();
            while(writeBuffer.hasRemaining()) {
                System.out.println("写回数据");
                socketChannel.write(writeBuffer);//将数据写回去
            }

            System.out.println("关闭连接");
            selectionKey.cancel();//这是在干嘛？--关闭连接？
        }

        /**
         * 写事件处理
         */
        private void read() throws IOException {
            try {
                /**
                 * 读事件处理
                 */
                while (socketChannel.isOpen() && socketChannel.read(readBuffer) != -1) {
                    //读到了数据
                    if (readBuffer.position() > 1) {
                        break;//有真实数据写入，调出循环
                    }
                }
                readBuffer.flip();//切换读模式
                System.out.println("读到来自客户端的数据：  " + new String(readBuffer.array(), readBuffer.position(), readBuffer.limit(),Charset.defaultCharset()));//将数据读出

//                new Processer().run();//单线程模式同步处理业务逻辑

                threadPool.execute(new Processer());//使用线程池异步处理业务逻辑
            } catch (Exception e) {

            }
        }
        /**
         *
         * handler的内部类
         *业务逻辑的处理
         *
         */
        private class Processer implements Runnable {
            @Override
            public void run() {
                try {
//                    synchronized (this) {//同步控制
//
//                    }
                    Thread.sleep(10);//模拟10毫秒的业务处理耗时
                    System.out.println("处理了10毫秒业务逻辑");
                    state = writestate;
                    selectionKey.interestOps(SelectionKey.OP_WRITE);//修改兴趣，对写事件感兴趣的监听

                    selectionKey.selector().wakeup();//这是关键一步！！
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        }
//        /**
//         *
//         * handler的内部类
//         *业务逻辑的处理
//         *
//         */
//        private class Reader implements Runnable {
//            @Override
//            public void run() {
//                try {
//                    /**
//                     * 读事件处理
//                     */
//                    while (socketChannel.isOpen() && socketChannel.read(readBuffer) != -1) {
//                        //读到了数据
//                        if (readBuffer.position() > 1) {
//                            break;//有真实数据写入，调出循环
//                        }
//                    }
//                    threadPool.execute(new Processer());
//                } catch (Exception e) {
//
//                }
//
//            }
//        }
//
//        /**
//         *
//         * handler的内部类
//         *业务逻辑的处理
//         *
//         */
//        private class Writer implements Runnable {
//            @Override
//            public void run() {
//                try {
//                    synchronized (Processer.class) {//同步控制
//                        Thread.sleep(10);//模拟10毫秒的业务处理耗时
//                        System.out.println("处理了10毫秒业务逻辑");
//                        state = writestate;
//                        selectionKey.interestOps(SelectionKey.OP_WRITE);//修改兴趣，对写事件感兴趣的监听
//                    }
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//
//            }
//        }
    }



}
