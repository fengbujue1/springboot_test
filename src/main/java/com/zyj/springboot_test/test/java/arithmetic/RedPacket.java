package com.zyj.springboot_test.test.java.arithmetic;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class RedPacket {
    public static void main(String[] args) {
        int errorTimes = 0;
        for (int i = 0; i < 10000; i++) {
            System.out.println("开始红包生成算法：");
            int amount = 10000;
            int quantity = 10;
            System.out.println("总金额：" + amount + "；     红包总数：" + quantity);
            ArrayList<Integer> redPackets = randomRedPacket(amount, quantity);

            int sum = 0;
            for (Integer redPacket : redPackets) {
                System.out.println("数额:" + redPacket);
                sum += redPacket;
            }
            System.out.println("总额:" + sum);
            if (sum!=amount) {
                errorTimes++;
            }
        }
        System.out.println("发生金额不符次数：" + errorTimes);

    }

    private static ArrayList<Integer> randomRedPacket(int amount, int quantity) {

        //根据总领取数quantity把总额切割成 quantity个线段

        ArrayList<Integer> temp = new ArrayList<>();

        //切割这个线段 quantity-1次，若重复则重来
        Random random = new Random();
        for (int i = 0; i < quantity - 1; i++) {
            int point = random.nextInt(amount);
            if (temp.contains(point)) {
                i--;
            } else {
                temp.add(point);
            }
        }

        //将切割出来的线段排序
        Collections.sort(temp);

        //根据切割出来的线段 把红包里的金额进行分割存储
        ArrayList<Integer> redPacket = new ArrayList<>();
        for (int i = 0; i < temp.size(); i++) {
            if (i == 0) {
                redPacket.add(temp.get(i));
                continue;
            }
            if (i == (temp.size() - 1)) {
                redPacket.add(amount - temp.get(i));
                redPacket.add(temp.get(i) - temp.get(i-1));
                continue;
            }
            redPacket.add(temp.get(i) - temp.get(i-1));
        }
        return redPacket;

    }
}
