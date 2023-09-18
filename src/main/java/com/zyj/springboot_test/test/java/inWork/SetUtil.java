package com.zyj.springboot_test.test.java.inWork;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * @Author 周赟吉
 * @Date 2023/7/5 16:44
 * @Description :
 */
public class SetUtil {
    public static void main(String[] args) throws IOException {
        String excelPath = "C:\\Users\\king\\Desktop\\新建文本文档 (2).txt";
        BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(excelPath),"utf-8"));
        String line;
        String prior = "";
        ArrayList<String> strings = new ArrayList<>();
        while ((line = reader.readLine()) != null) {
            String[] split = line.split("---");
            String s = split[2];
            char c = s.charAt(0);
            String s1 = String.valueOf(c);
            String s2 = s1.toUpperCase();
            System.out.println(".set" + s2 + s.substring(1,s.length()-1)+"(null)");
        }
    }
}
