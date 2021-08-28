package com.zyj.springboot_test.test.java.netWork.nioDemo;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;

/**
 * NIO下的UPD用户数据报服务。
 *
 */
public class DatagramServer {
	
	public static void main(String[] args) {
		start(9999);
	}
	
	public static void start(int port) {
		// 打开 DatagramChannel
		try {
			DatagramChannel channel = DatagramChannel.open();
			channel.configureBlocking(false);
			// 在UDP端口9999上接收数据包
			channel.socket().bind(new InetSocketAddress(port));
			
			ByteBuffer buf = ByteBuffer.allocate(48);
			buf.clear();
			// receive将接收到的数据包内容复制到指定的Buffer
			// Buffer容不下收到的数据，多出的数据将被丢弃
			channel.receive(buf);
			// channel.read(dst); //当然也可以通过read方法读取数据
			
			// 发送数据
			buf.flip();
			while(buf.hasRemaining()) {
				// 与tcp建立连接不同，udp是无连接的，发送数据需要指定发送的地址和端口
				// 服务端并没有监控这个端口，所以什么也不会发生。
				// 也不会通知发出的数据包是否已收到，因为UDP在数据传送方面没有任何保证。
				int bytesSent = channel.send(buf, new InetSocketAddress("localhost", 80));
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
