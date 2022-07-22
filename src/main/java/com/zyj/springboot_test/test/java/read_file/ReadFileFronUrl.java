package com.zyj.springboot_test.test.java.read_file;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * @Author 周赟吉
 * @Date 2022/5/27 16:05
 * @Description :
 */
public class ReadFileFronUrl {
    public static void main(String[] args) throws IOException {
        String url = "https://file.cloudpnr.com/app-bdef1811-3bad-472f-b76c-90230ad840ed%2F3af58554-078c-11ed-861b-0242ac11000b.zip?Expires=1658483710&Signature=0%2B4ClA%2FdgW8OXjbJUWE2WRQWJQk%3D&OSSAccessKeyId=LTAI6Yzq9tIYS57h";
        String destPath = "D:\\project_my\\tmpFile";
        downLoadByUrl(url, "detail.zip", destPath);
    }

    public static void  downLoadByUrl(String urlStr,String fileName,String savePath) throws IOException {
        URL url = new URL(urlStr);
        HttpURLConnection conn = (HttpURLConnection)url.openConnection();
        //设置超时间为3秒
        conn.setConnectTimeout(5*1000);
        //防⽌屏蔽程序抓取⽽返回403错误
        conn.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");
        //得到输⼊流
        InputStream inputStream = conn.getInputStream();
        //⽂件保存位置
        File saveDir = new File(savePath);
        if(!saveDir.exists()){
            saveDir.mkdir();
        }
        File file = new File(saveDir+File.separator+fileName);
        FileOutputStream fos = new FileOutputStream(file);
        byte[] buffer = new byte[1024];
        int len = 0;
        while ((len = inputStream.read(buffer)) != -1) {
            //todo 这种读取方式在读取压缩包的时候可能会乱码，因为流输入的字节不一定能占满buffer的1024个位置
            fos.write(buffer);
            //这种读取方式才能正确输出压缩文件
//            fos.write(buffer, 0, len);
        }
        if(fos!=null){
            fos.close();
        }
        if(inputStream!=null){
            inputStream.close();
        }
        System.out.println("info:"+url+" download success");
    }
}
