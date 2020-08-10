package com.zyj.springboot_test.test.java.netWork.bio;

import java.io.*;
import java.net.Socket;
import java.nio.charset.Charset;
import java.util.Scanner;

public class ClientTest {
    private static final Charset charset = Charset.forName("UTF-8");

    public static void main(String[] args) throws IOException, InterruptedException {
        Socket socket = new Socket("localhost", 8088);//建立一个链接，
        OutputStream outputStream = socket.getOutputStream();//打开流
        while (true) {

            System.out.println("输入数据：");
            Scanner scanner = new Scanner(System.in);
            String msg = scanner.nextLine()+"\r\n";

            System.out.println("写入数据");
            outputStream.write(msg.getBytes(charset));//将控制台的输入，以特定编码形式写入 输出流


            InputStream inputStream = socket.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, "utf-8"));
//            String resp = "";
//            System.out.println("返回的信息");
//            while ((resp = reader.readLine()) != null) {
//                System.out.println(resp);
//            }

            outputStream.flush();//
        }
    }
}
