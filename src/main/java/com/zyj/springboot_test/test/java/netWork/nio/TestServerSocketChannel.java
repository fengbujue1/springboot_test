package com.zyj.springboot_test.test.java.netWork.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;

public class TestServerSocketChannel {
    public static void main(String[] args) throws IOException {
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.configureBlocking(false);//设定为非阻塞，默认是阻塞的

        System.out.println("服务器启动完成");

        serverSocketChannel.bind(new InetSocketAddress(8088));//绑定端口
        while (true) {//循环遍历接受连接请求
            System.out.println("循环");
            SocketChannel accept = serverSocketChannel.accept();//非阻塞获取连接
            if (accept != null) {
                ByteBuffer byteBuffer = ByteBuffer.allocate(1024);//申请一段缓冲
                accept.configureBlocking(false);

                while (accept.isOpen() && accept.read(byteBuffer) != -1) {
                    //读到了数据
                    if (byteBuffer.position() > 1) {
                        break;//有真实数据写入，调出循环
                    }
                }
                byteBuffer.flip();//切换为读模式
                System.out.println("读到来自客户端的数据：  " + new String(byteBuffer.array(), byteBuffer.position(), byteBuffer.limit(),Charset.defaultCharset()));//将数据读出

                byteBuffer.clear();//清空数据
//                byteBuffer.compact();//切换为写模式（清空后应该默认就是写模式了，不用再次转换）
//                ByteBuffer responseBuffer = ByteBuffer.wrap("hello".getBytes(Charset.forName("utf8")));
                byteBuffer.put("hello world".getBytes(Charset.forName("utf8")));

                byteBuffer.flip();
                while(byteBuffer.hasRemaining()) {
                    System.out.println("写回数据");
                    accept.write(byteBuffer);//将数据写回去
                }

            }
        }
    }
}
