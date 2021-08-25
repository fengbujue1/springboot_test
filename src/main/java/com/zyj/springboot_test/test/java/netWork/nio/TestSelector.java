package com.zyj.springboot_test.test.java.netWork.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.nio.charset.Charset;
import java.util.Iterator;
import java.util.Set;

public class TestSelector {
    public static void main(String[] args) throws IOException {

        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.configureBlocking(false);//设定为非阻塞，默认是阻塞的

        serverSocketChannel.bind(new InetSocketAddress(8088));//绑定端口

        Selector selector = Selector.open();
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);//注册连接事件的监听

        System.out.println("服务器启动成功");

        while (true) {
            if (selector.select() != 0) {//阻塞等待有事件被触发
                Set<SelectionKey> selectionKeys = selector.selectedKeys();
                Iterator<SelectionKey> iterator = selectionKeys.iterator();
                if (iterator.hasNext()) {//遍历处理当前堆积的事件
                    SelectionKey selectionKey = iterator.next();
                    iterator.remove();//读取完成，移除当前这个事件

                    if (selectionKey.isAcceptable()) {//如果是客户端发起连接
                        System.out.println("可接收事件");
                        SocketChannel socketChannel = ((ServerSocketChannel) selectionKey.channel()).accept();
                        socketChannel.configureBlocking(false);
                        socketChannel.register(selector, SelectionKey.OP_READ);//注册这个通道，监听连接事件，读事件，写事件
                        System.out.println("收到新连接：" + socketChannel);
                    }
                    else if (selectionKey.isConnectable()) {
                        System.out.println("连接事件");
                    }
                    else if (selectionKey.isReadable()) {
                        System.out.println("读取事件");
                        SocketChannel accept = (SocketChannel)selectionKey.channel();
                        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);//申请一段缓冲
                        accept.configureBlocking(false);
                        while (accept.isOpen() && accept.read(byteBuffer) != -1) {
                            //读到了数据
                            if (byteBuffer.position() > 1) {
                                break;//有真实数据写入，调出循环
                            }
                        }
                        byteBuffer.flip();//切换为读模式
                        String s = new String(byteBuffer.array(), byteBuffer.position(), byteBuffer.limit(), Charset.defaultCharset());
                        System.out.println("读到来自客户端的数据：  " + s);//将数据读出

                        byteBuffer.clear();//清空数据

                        SocketChannel channel = (SocketChannel)selectionKey.channel();
//                        ByteBuffer responseBuffer = ByteBuffer.wrap("hello".getBytes(Charset.forName("utf8")));
                        if (s.equals("bye")) {
                            while(byteBuffer.hasRemaining()) {
                                byteBuffer.put("bye".getBytes(Charset.forName("utf8")));
                                System.out.println("写回数据");
                                channel.write(byteBuffer);//将数据写回去
                            }
                            channel.close();//关闭连接
                            continue;
                        } else {
                            while(byteBuffer.hasRemaining()) {
//                                byteBuffer.compact();
                                byteBuffer.put("hello".getBytes(Charset.forName("utf8")));
                                System.out.println("写回数据");
                                channel.write(byteBuffer);//将数据写回去
                            }
//                            accept.register(selector, SelectionKey.OP_READ);
                            accept.register(selector, SelectionKey.OP_WRITE);//注册一个写入事件，给客户端写一个回信
                        }

//                        channel.close();//关闭连接
//                        accept.register(selector, SelectionKey.OP_WRITE);//注册一个写入事件，给客户端写一个回信
                    }
                    else if (selectionKey.isWritable()) {
                        System.out.println("写入事件");
//                        SocketChannel accept = (SocketChannel)selectionKey.channel();
//                        accept.register(selector, SelectionKey.OP_READ);

                    }

                }
            }
        }

    }
}
