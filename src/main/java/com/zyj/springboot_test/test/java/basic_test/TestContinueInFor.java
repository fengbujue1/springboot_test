package com.zyj.springboot_test.test.java.basic_test;

public class TestContinueInFor {
    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            System.out.println("i:" + i);
            for (int j = 0; j < 10; j++) {
                if (j > 0) {
                    continue;
                }
                System.out.println("j:" + j);
            }
        }
    }
}
