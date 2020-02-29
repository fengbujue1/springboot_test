package com.zyj.springboot_test.test.java.arithmetic;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

/**
 * 抢红包算法，2倍均值法
 */
public class RedPacket2 {
    public static void main(String[] args) {
        ArrayList<Integer> errorr = new ArrayList<>();
        int errorTimes = 0;
        for (int i = 0; i < 10000; i++) {
            System.out.println("开始红包生成算法：");
            int amount = 10000;
            int quantity = 10;
            System.out.println("总金额：" + amount + "；     红包总数：" + quantity);
            ArrayList<Integer> redPackets = randomRedPacket2(amount, quantity);

            int sum = 0;
            for (Integer redPacket : redPackets) {
                System.out.println("数额:" + redPacket);
                sum += redPacket;
            }
            System.out.println("总额:" + sum);
            if (sum!=amount) {
                errorr.add(amount - sum);
                errorTimes++;
            }
        }
        System.out.println("发生金额不符次数：" + errorTimes);
        System.out.println("差额：" + errorr);

    }

    private static ArrayList<Integer> randomRedPacket2(int amount, int quantity) {
        ArrayList<Integer> redPacket = new ArrayList<>();
        //取出均值界限
        Random random = new Random();
        int amount2 = amount;
        int residuePacket = quantity;
        for (int i = 0; i < quantity ; i++) {
            if (i == quantity - 1) {
                redPacket.add(amount);
                break;
            }
            int bound = amount2 / quantity * 2;
            int tempCount = 0;
            boolean flag = true;
            while (tempCount == 0 || flag) {
                tempCount = random.nextInt(bound);
                int residue = amount -tempCount;
                if (tempCount != 0&& residue > residuePacket - 1) {
                    residuePacket--;
                    amount -= tempCount;
                    flag = false;
                }
            }
            redPacket.add(tempCount);
        }
        return redPacket;

    }
}
