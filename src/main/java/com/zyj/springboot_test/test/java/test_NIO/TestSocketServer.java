package com.zyj.springboot_test.test.java.test_NIO;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.Set;

public class TestSocketServer {
    public static void main(String[] args ) throws Exception{
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.bind(new InetSocketAddress(8080));
        serverSocketChannel.configureBlocking(false);

        Selector selector = Selector.open();
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);//注册
        System.out.println("启动成功");
        while (true) {
            int readyChannels = selector.select();
            if (readyChannels == 0) {
                System.out.println("readyChannels空的");
                continue;
            }
            Set<SelectionKey> keys = selector.keys();
            Iterator<SelectionKey> iterator = keys.iterator();
            while (iterator.hasNext()) {
                SelectionKey selectionKey = iterator.next();
                if (selectionKey.isAcceptable()) {
                    SocketChannel accept = ((ServerSocketChannel) selectionKey.channel()).accept();
                    ByteBuffer buffer = ByteBuffer.allocate(1024);
                    if (accept != null) {
                        accept.read(buffer);
                        buffer.flip();
                        byte[] bytes = new byte[buffer.limit()];
                        buffer.get(bytes);
                        System.out.println("收获到客户端消息：" + new String(bytes));

                    }
                }
            }

        }

//        ByteBuffer buffer = ByteBuffer.allocate(1024);
//        while (true) {
//            SocketChannel accept = serverSocketChannel.accept();
//            if (accept != null) {
//                accept.read(buffer);
//                buffer.flip();
//                byte[] bytes = new byte[buffer.limit()];
//                buffer.get(bytes);
//                System.out.println("收获到客户端消息：" + new String(bytes));
//
//            }
//        }


    }
}
