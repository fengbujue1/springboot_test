package com.zyj.springboot_test.test.java.netWork.nioDemo;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

public class ServerSocketChannelDemo {
    public static void main(String[] args) throws IOException {
    	ServerSocketChannel serverChann = ServerSocketChannel.open();
    	serverChann.configureBlocking(false);	// 默认阻塞，配置为false表示不阻塞
    	serverChann.bind(new InetSocketAddress(8080));
    	while(true) {
    		// 非阻塞
    		SocketChannel socketChann = serverChann.accept();
    		if(socketChann != null) {
    			// 超过容量，意味着你不知道数据的长度，协议报文，长度
    			ByteBuffer dst = ByteBuffer.allocate(1024);
    			// read非阻塞的
    			while(socketChann.isOpen() && socketChann.read(dst) > -1) {
    				// 表示读到了客户端数据，跳出 
    				if(dst.position() > 0) break;
    			}
    			
    			dst.flip();	// 转换为读模式
    			System.out.println(new String(dst.array(), dst.position(), dst.limit()));
    			
    			dst.clear();
    			dst.put("hello world".getBytes());
    			while(dst.hasRemaining()) {
    				socketChann.write(dst);
    			}
    		}
    		
    	}


    }
}
