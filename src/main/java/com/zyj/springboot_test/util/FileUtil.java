package com.zyj.springboot_test.util;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;

public class FileUtil {
    public static String readFileFromClassPath(String filePath) {
        String ileClassPath = getFullClassPath(filePath);
        BufferedReader bufferedReader;
        StringBuilder sb = new StringBuilder();
        String s;
        try {
            bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(ileClassPath)));
            while ((s = bufferedReader.readLine()) != null) {
                sb.append(new String(s.getBytes(),"utf-8"));
                //lua脚本读取的时候，换行符必须自己添加，因为用的是readline方法，
                //否则拼接出来的字符串就是一整行，传进redis 中会报错（或者直接在本地就报错了）
                sb.append("\n");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return sb.toString();
    }

    /**
     * 获取文件的类路径
     * @param fileName
     * @return
     */
    public static String getFullClassPath(String fileName) {
        String path = FileUtil.class.getResource("/").getPath();
        return path + fileName;
    }
}
