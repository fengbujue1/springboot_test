package com.zyj.springboot_test.test.java.basic_test;

import java.util.Random;

public class CalculationTest {
    public static void main(String[] args) {
//        test1();
//        test2();
        test3();
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

    /**
     * 测试   Random.nextInt()方法的边界
     */
    public static void test2() {
        int low = 0;
        int high = 2;
        Random random = new Random();
        for (int i = 0; i < 200; i++) {
            System.out.println(random.nextInt(high));
        }
    }
    /**
     * 测试   一种算法，多个大数据间的间隔随机数
     */
    public static void test3() {
        long step = 100;
        long min = 3600/step;
        long max = 4400/step;
        Random random = new Random();
        for (int i = 0; i < 200; i++) {
            long count = (min + random.nextInt((int) (max - min + 1))) * step;
            System.out.println(count);
        }
        System.out.println(random.nextInt(0));
    }
}
