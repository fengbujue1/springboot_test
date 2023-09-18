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
public class GetUtil {
    public static void main(String[] args) throws IOException {
        String excelPath = "C:\\Users\\king\\Desktop\\新建文本文档 (2).txt";
        BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(excelPath),"utf-8"));
        String line;
        String prior = "";
        ArrayList<String> strings = new ArrayList<>();
        while ((line = reader.readLine()) != null) {
            String[] split = line.split("\\|");
            String s = split[1];
            StringBuilder sb = new StringBuilder();
            String[] split1 = s.split("_");
            for (int i = 0; i < split1.length; i++) {
                String s1 = split1[i];
                String s2 = String.valueOf(s1.charAt(0));
                sb.append(s2.toUpperCase() + s1.substring(1));
            }
            System.out.println("rd.get" + sb.toString()+"();");
        }
    }
}
