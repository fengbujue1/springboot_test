package com.zyj.springboot_test.test.java.basic_test;

import java.util.Random;

public class RandomTest {
    public static void main(String[] args) {
        Random random = new Random();
        for (int i = 0; i < 1000; i++) {
            System.out.println(random.nextInt(100));
        }
    }
}
