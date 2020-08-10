package com.zyj.springboot_test.test.java.netWork.bio;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerTest {
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(8088);
        Runtime.getRuntime().addShutdownHook(new Thread(){
            @Override
            public void run() {
                try {
                    serverSocket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        System.out.println("服务器启动成功");

        while (true) { //循环监听
            System.out.println("调用accept()方法");
            Socket accept = serverSocket.accept(); // 准备接受数据，没有获取到就阻塞

            System.out.println("调用inputStream方法");
            InputStream inputStream = accept.getInputStream();//获取输入流
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, "utf-8"));
            String msg,resp="1";
            while ((msg = reader.readLine()) != null) {//循环读取数据，阻塞等待
                if (msg.length() == 0) {
                    break;
                }
                System.out.println(msg);
            }
//            msg = reader.readLine();
//            System.out.println(msg);
            System.out.println("收到数据,来自："+ accept.toString());
            accept.getOutputStream().write(resp.getBytes("utf-8"));
            accept.getOutputStream().flush();
            accept.close();//主动关闭连接，代码走到这一步，客户端如果还要传输数据，需要重新建立连接
        }

    }
}
