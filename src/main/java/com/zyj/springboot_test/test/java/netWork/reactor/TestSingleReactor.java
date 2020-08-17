package com.zyj.springboot_test.test.java.netWork.reactor;

import io.netty.channel.ServerChannel;
import org.apache.ibatis.annotations.Select;

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

/**
 *   Reactor模式，无非就是将原来一个人干的事情进行的分类拆分，拆分成适合多个岗位的人干。
 *   比如连接请求处理，交给Acceptor；客户端数据读取写入处理，交给Handler；IO多路复用，交给Reactor；<br/>
 *
 *      selector完成IO多路复用事件的派发
 *
 */

public class TestSingleReactor {
    public static void main(String[] args) throws IOException {
        SingleReactor reactor = new SingleReactor(8088);
        reactor.run();

    }

}

class SingleReactor implements Runnable {

    final Selector selector;
    final ServerSocketChannel serverSocketChannel;

    /**
     * 构造器 做一些 初始操作
     * 1.监听通道开启
     * 2.端口绑定。
     * 3.事件监听注册
     * 4.事件处理器绑定
     */
    public SingleReactor(int port) throws IOException{

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
     * 内部类
     *
     * 用于将连接实例化，然后交由特定的 处理器处理
     */
    private class Accpter implements Runnable {
        final SelectionKey selectionKey;
        final ServerSocketChannel serverSocketChannel;

        public Accpter(SelectionKey selectionKey) {
            this.selectionKey = selectionKey;
            this.serverSocketChannel = (ServerSocketChannel) selectionKey.channel();
        }

        @Override
        public void run() {
            try {
                System.out.println("阻塞等待连接建立");
               SocketChannel socketChannel = serverSocketChannel.accept();//建立一个和客户端的连接,阻塞
                if (socketChannel != null) {
                    System.out.println("建立一个和客户端的连接");
                    new Handler(selector, socketChannel);
                }

            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    final class Handler implements Runnable {

        final SelectionKey selectionKey;
        final SocketChannel socketChannel;

        ByteBuffer writeBuffer = ByteBuffer.allocate(1024);//申请一段写内存
        ByteBuffer readBuffer = ByteBuffer.allocate(1024);//申请一段读内存
        static final int readstate=0, writestate = 1;//定义读写两个状态

        int state = readstate;//初始默认为读状态


        public Handler(Selector selector, SocketChannel socketChannel) throws IOException {
            this.socketChannel = socketChannel;
            socketChannel.configureBlocking(false);//设为非阻塞模式

            //将这个与客户端的连接注册到选择器中，
            selectionKey = socketChannel.register(selector, 0);
            selectionKey.attach(this);//同时把自己作为事件处理器

            System.out.println("绑定处理器，并唤醒等待线程");
            selectionKey.interestOps(SelectionKey.OP_READ);//对读事件感兴趣

            selector.wakeup();//唤醒select()方法上等待着的线程，告诉他他感兴趣的是发生了变化

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
         * 读事件处理
         */
        private void read() throws IOException {
            while (socketChannel.isOpen() && socketChannel.read(readBuffer) != -1) {
                //读到了数据
                if (readBuffer.position() > 1) {
                    break;//有真实数据写入，调出循环
                }
            }
            readBuffer.flip();//切换读模式
            System.out.println("读到来自客户端的数据：  " + new String(readBuffer.array(), readBuffer.position(), readBuffer.limit(),Charset.defaultCharset()));//将数据读出

            try {
                Thread.sleep(10);//模拟10毫秒的业务处理耗时
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            state = writestate;
            selectionKey.interestOps(SelectionKey.OP_WRITE);//修改兴趣，对写事件感兴趣的监听
        }
        /**
         * 写事件处理
         */
        private void write() throws IOException {
            writeBuffer.put("hello world".getBytes(Charset.forName("utf8")));

            writeBuffer.flip();
            while(writeBuffer.hasRemaining()) {
                System.out.println("写回数据");
                socketChannel.write(writeBuffer);//将数据写回去
            }

            selectionKey.cancel();//这是在干嘛？--关闭连接？
        }
    }

}
