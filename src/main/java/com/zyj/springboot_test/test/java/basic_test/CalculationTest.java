package com.zyj.springboot_test.test.java.basic_test;

public class CalculationTest {
    public static void main(String[] args) {
        test1();
    }

    /**
     * 负数取模，未超过 为0
     */
    public static void test1() {
        long i = -100;
        System.out.println(i / 3600);
        i = -4500;
        System.out.println(i / 3600);

    }
}
