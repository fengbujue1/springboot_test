package com.zyj.springboot_test.test.test_NIO.select;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectableChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Scanner;
import java.util.Set;

public class ClientChannelSelect {
    public static void main(String[] args) throws Exception{
        //准备工作
        SocketChannel channel = SocketChannel.open();//开启通道
        Selector selector = Selector.open();//打开选择器
        channel.configureBlocking(false);//设为异步
        channel.register(selector, SelectionKey.OP_CONNECT);//注册到选择器
        channel.connect(new InetSocketAddress("localhost",8080));//连接


        while (true) {
            selector.select();
            Set<SelectionKey> selectionKeys = selector.selectedKeys();
            Iterator<SelectionKey> iterator = selectionKeys.iterator();
            while (iterator.hasNext()) {

                SelectionKey selectionKey = iterator.next();
                iterator.remove();
                if (selectionKey.isConnectable()) {
                    System.out.println("可连接状态");
                    SelectableChannel channelConnectable = selectionKey.channel();
                    try {
                        if (channel.finishConnect()) { // 完成连接
                            // 连接成功
                            System.out.println("连接成功-" + channel);

                            ByteBuffer buffer = ByteBuffer.allocateDirect(20480);

                            // 切换到感兴趣的事件
                            selectionKey.attach(buffer);
                            selectionKey.interestOps(SelectionKey.OP_WRITE);
                        } else {
                            System.out.println("连接未完成");

                        }
                    } catch (IOException e) {
                        // 连接失败
                        e.printStackTrace();
                        return;
                    }
                } else if (selectionKey.isReadable()) {
                    System.out.println("可读取状态");

                    ByteBuffer buffer = ByteBuffer.allocate(20480);
                    SocketChannel socketChannel = (SocketChannel)selectionKey.channel();
                    while (socketChannel.isOpen() && socketChannel.read(buffer) != -1) {
                        // 长连接情况下,需要手动判断数据有没有读取结束 (此处做一个简单的判断: 超过0字节就认为请求结束了)
                        if (buffer.position() > 0) break;
                    }
                    if (buffer.position() == 0) continue; // 如果没数据了, 则不继续后面的处理
                    buffer.flip();
                    byte[] content = new byte[buffer.remaining()];
                    buffer.get(content);
                    System.out.println(new String(content));
                }else if (selectionKey.isAcceptable()) {
                    System.out.println("可接收状态");
                    SelectableChannel channelAcceptable = selectionKey.channel();


                }else if (selectionKey.isWritable()) {
                    System.out.println("可写入状态");
                    ByteBuffer buffer = (ByteBuffer)selectionKey.attachment();//取出存入的对象
                    buffer.clear();
                    Scanner scanner = new Scanner(System.in);
                    System.out.print("请输入：");
                    // 发送内容
                    String msg = scanner.nextLine();
                    scanner.close();

                    buffer.put(msg.getBytes());
                    buffer.flip();
                    channel.write(buffer);
                    while (buffer.hasRemaining()) {
                        channel.write(buffer);
                    }
//                    channel.close();
//                     切换到感兴趣的事件
                    selectionKey.interestOps(SelectionKey.OP_READ);

                }
            }
        }
    }
}
