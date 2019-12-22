package com.zyj.springboot_test.test.java.read_file;


import java.io.*;
import java.util.Iterator;
import java.util.LinkedList;

public class TestFileInputReader {
    public static void main(String args[]) throws Exception {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream("C:\\Users\\Admin\\Desktop\\学习计划.txt"),"gbk"));
        String s;
        LinkedList<String> strings = new LinkedList<>();
        File des = new File("C:\\Users\\Admin\\Desktop\\temp\\1.txt");
        PrintWriter printWriter = new PrintWriter(new BufferedWriter(new FileWriter(des)));
        int lineCount = 0;
        try {
            while ((s = bufferedReader.readLine()) != null) {
                strings.add(new String(s.getBytes(),"utf-8"));

            }
            Iterator<String> stringIterator = strings.descendingIterator();
            while (stringIterator.hasNext()) {
                String s2 =stringIterator.next();
                System.out.println(s2);
                printWriter.println(lineCount++ + ":" +s2);
            }
        } catch (IOException e) {
            System.out.println("wrong");

        } finally {
            printWriter.close();
            bufferedReader.close();
        }


    }
}
