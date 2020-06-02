package com.zyj.springboot_test.gameCalc;

import java.util.Scanner;

public class Test {
    public static void main(String[] args) {
        for (int i = 5; i < 10; i++) {
            double attact = i*1000000 * (1-0.125*3) / 4.5;
            System.out.println("血量：" + i + "百万" + "    攻击：" + (attact+100000));
        }

        System.out.println("加20%爆伤");
        for (int i = 5; i < 10; i++) {
            double attact = i*1000000 * (1-0.125*3.2) / 4.5;
            System.out.println("血量：" + i + "百万" + "    攻击：" + (attact+100000));
        }
    }
}
