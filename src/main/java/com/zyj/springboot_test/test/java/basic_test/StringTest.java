package com.zyj.springboot_test.test.java.basic_test;

public class StringTest {
    public static void main(String args[]) {
        String a = "asd";
        String b = new String("asd");
        b.intern();
        String c = "asd";
        String d = new String("asd");

        System.out.println(a == b);
        System.out.println(c == a);
        System.out.println(b == d);
        System.out.println(b == c);
    }
}
