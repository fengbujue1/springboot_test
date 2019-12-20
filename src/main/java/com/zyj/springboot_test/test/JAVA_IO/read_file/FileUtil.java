package com.zyj.springboot_test.test.JAVA_IO.read_file;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
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
