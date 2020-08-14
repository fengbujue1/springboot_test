package com.zyj.springboot_test.test.java.netWork;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.concurrent.CountDownLatch;

/**
 * 通过控制台输入模拟客户端请求
 * 
 */
public class ConcurrentClientTest {
    private static Charset charset = Charset.forName("UTF-8");

	public static void main(String[] args) throws Exception {
		ConcurrentClientTest concurrentClientTest = new ConcurrentClientTest();
		concurrentClientTest.testConcurrent();
	}
    
    private  void testConcurrent() {
    	long s = System.currentTimeMillis();
    	int concurrentLevel = 30;
    	
    	CountDownLatch latch = new CountDownLatch(1);
    	CountDownLatch countDown = new CountDownLatch(concurrentLevel);
    	
        for(int i = 0; i < concurrentLevel; i++) {
			new Task(i, latch, countDown).start();
        }
        latch.countDown();
        try {
			countDown.await();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
    	System.out.println("cost: "+(System.currentTimeMillis() - s)+" ms");
    }
    
    private void sendData_Nio(int i) {
    	try {
			SocketChannel socketChannel = SocketChannel.open();
			socketChannel.connect(new InetSocketAddress("127.0.0.1", 8088));

			socketChannel.configureBlocking(false);//设置为非阻塞

			ByteBuffer byteBuffer = ByteBuffer.allocate(1024);//申请内存


			while (!socketChannel.finishConnect()) {
				// 没连接上,则一直等待
				Thread.yield();
			}

			System.out.println("输入数据：");
			byteBuffer.put(("test"+i).getBytes(Charset.defaultCharset()));
			byteBuffer.flip();
			while (byteBuffer.hasRemaining()) {
				socketChannel.write(byteBuffer);//持续写入数据
			}

			//准备读取服务端的响应
			ByteBuffer respBuffer = ByteBuffer.allocate(1024);
			while (socketChannel.isOpen() && socketChannel.read(respBuffer) != -1) {
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

			socketChannel.close();//关闭连接
    	} catch (UnknownHostException e) {
    		e.printStackTrace();
    	} catch (UnsupportedEncodingException e) {
    		e.printStackTrace();
    	} catch (IOException e) {
    		e.printStackTrace();
    	}
    }

	private void sendData_Bio() {
		try {
			Socket socket = new Socket("localhost", 8088);//建立一个链接，
			OutputStream outputStream = socket.getOutputStream();//打开流

			System.out.println("写入数据");
			outputStream.write("test".getBytes(charset));//将控制台的输入，以特定编码形式写入 输出流


			InputStream inputStream = socket.getInputStream();
			BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, "utf-8"));
//            String resp = "";
//            System.out.println("返回的信息");
//            while ((resp = reader.readLine()) != null) {
//                System.out.println(resp);
//            }

			outputStream.flush();//
		} catch (Exception e) {

		}

    }

	class Task extends Thread {
		private int num;
		private CountDownLatch latch;
		private CountDownLatch countDown;

		public Task(int num,CountDownLatch latch,CountDownLatch countDown) {
			this.num = num;
			this.latch = latch;
			this.countDown = countDown;
		}

		@Override
		public void run() {
			ConcurrentClientTest client = new ConcurrentClientTest();
			try {
				latch.await();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			client.sendData_Nio(num);
			countDown.countDown();
		}
	}
}

