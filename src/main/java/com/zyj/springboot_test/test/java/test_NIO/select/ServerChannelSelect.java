package com.zyj.springboot_test.test.java.test_NIO.select;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.Set;

public class ServerChannelSelect {
    public static void main(String[] args) throws Exception{
        //准备工作
        ServerSocketChannel channel = ServerSocketChannel.open();//开启通道
        Selector selector = Selector.open();//打开选择器
        channel.bind(new InetSocketAddress( 8080));//连接
        channel.configureBlocking(false);//设为异步
        channel.register(selector, SelectionKey.OP_ACCEPT);//注册到选择器
        System.out.println("启动成功");

        while (true) {
            selector.select();
            Set<SelectionKey> selectionKeys = selector.selectedKeys();
            Iterator<SelectionKey> iterator = selectionKeys.iterator();
            while (iterator.hasNext()) {
                SelectionKey selectionKey = iterator.next();
                iterator.remove();
                if (selectionKey.isConnectable()) {
                    System.out.println("可连接状态");


//                    SelectableChannel channelConnectable = selectionKey.channel();
//
//                    selectionKey.interestOps(SelectionKey.OP_READ);
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
//                    Thread.sleep(10000);
//                    socketChannel.close();
                    // 响应结果 200
                    String response = "HTTP/1.1 200 OK\r\n" +
                            "Content-Length: 11\r\n\r\n" +
                            "Hello World";
                    ByteBuffer responseBuffer = ByteBuffer.wrap(response.getBytes());
                    while (responseBuffer.hasRemaining()) {
                        socketChannel.write(responseBuffer);
                    }


                }else if (selectionKey.isAcceptable()) {
                    System.out.println("可接收状态");
                    SelectableChannel channelAcceptable = selectionKey.channel();
                    SocketChannel socketChannel = ((ServerSocketChannel) selectionKey.channel()).accept();
                    socketChannel.configureBlocking(false);
                    socketChannel.register(selector, SelectionKey.OP_READ);
                    System.out.println("收到新连接：" + socketChannel);

                }else if (selectionKey.isWritable()) {
                    System.out.println("可写入状态");


                }
            }
        }
    }
}
