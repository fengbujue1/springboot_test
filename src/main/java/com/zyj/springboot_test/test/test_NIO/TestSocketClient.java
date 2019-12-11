package com.zyj.springboot_test.test.test_NIO;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

public class TestSocketClient {
    public static void main(String[] args) throws Exception{
        SocketChannel channel = SocketChannel.open();
        channel.configureBlocking(false);//设为非阻塞的
        channel.connect(new InetSocketAddress("127.0.0.1", 8080));//打开连接


        ByteBuffer buffer = ByteBuffer.allocate(1024);
        ByteBuffer wrap = buffer.wrap("hello".getBytes());
        System.out.println("发送数据" + wrap.toString());
        while (!channel.finishConnect()) {
            // 没连接上,则一直等待
            Thread.yield();
        }
        channel.write(wrap);//发送数据

        ByteBuffer requestBuffer = ByteBuffer.allocate(1024);
//        while (channel.isOpen() && channel.read(requestBuffer) != -1) {
//            System.out.println(channel.read(requestBuffer));
//        }
        channel.close();


    }
}
