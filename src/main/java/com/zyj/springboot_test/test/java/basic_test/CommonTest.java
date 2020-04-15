package com.zyj.springboot_test.test.java.basic_test;

public class CommonTest {
    public static void main(String[] args) {
        System.out.println(testReturn());
    }

    /**
     * return int++  和 return ++int
     * @return
     */
    public static int testReturn() {
        int i = 0;
//        return i++;
        return ++i;
    }
}
