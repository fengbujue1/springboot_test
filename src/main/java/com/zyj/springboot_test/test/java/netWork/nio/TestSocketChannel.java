package com.zyj.springboot_test.test.java.netWork.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.Scanner;
import java.util.logging.SocketHandler;

public class TestSocketChannel {
    public static void main(String[] args) throws IOException {
//        SocketChannel socketChannel = SocketChannel.open(new InetSocketAddress("127.0.0.1", 8088));
        SocketChannel socketChannel = SocketChannel.open();
        socketChannel.connect(new InetSocketAddress("127.0.0.1", 8088));

        socketChannel.configureBlocking(false);//设置为非阻塞
        Scanner scanner = new Scanner(System.in);

        while (true) {
            ByteBuffer byteBuffer = ByteBuffer.allocate(1024);//申请内存



            while (!socketChannel.finishConnect()) {
                // 没连接上,则一直等待
                Thread.yield();
            }

            System.out.println("输入数据：");
            String comad = scanner.nextLine();
            if ("quit".equals(comad)) {
                break;
            }
            byteBuffer.put(comad.getBytes(Charset.defaultCharset()));
            byteBuffer.flip();
            while (byteBuffer.hasRemaining()) {
                socketChannel.write(byteBuffer);//持续写入数据
            }

            //准备读取服务端的响应
            ByteBuffer respBuffer = ByteBuffer.allocate(1024);
            while (socketChannel.isOpen() && socketChannel.read(respBuffer) != -1) {
                System.out.println("在读取数据");
                //读到了数据
                if (respBuffer.position() > 1) {
                    break;//有真实数据写入，调出循环
                }
            }
//        System.out.println("收到来自服务器的数据： "+new String(respBuffer.array(), byteBuffer.position(), byteBuffer.limit()));//将数据读出
            respBuffer.flip();//切换为读模式

            byte[] content = new byte[respBuffer.limit()];
            respBuffer.get(content);
            System.out.println("收到来自服务器的数据： "+new String(content,Charset.forName("utf8")));
        }


        scanner.close();
        socketChannel.close();//关闭连接

    }
}
