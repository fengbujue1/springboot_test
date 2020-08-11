package com.zyj.springboot_test.test.java.netWork.nio;

import java.nio.ByteBuffer;

public class TestBuffer {
    //Buffer最重要的 三个变量 capacity (容量),limit（可读取的下标上限）,posotion（当前的下标）

    //写模式下，limit=capacity,position为从0开始的下标（递增）

    //读模式下，limit最后写入的数组下标，capacity,position为最后读取到的下标的下一位

    //从读取模式切换为写入模式，会把未读取的数据刷到数组最左边
    //从写入模式切换为读取模式，position变成写入模式的position,position则是从0开始

    /**
     * 错误使用模式可能导致的后果就是：
     *  1.写模式下 调用get方法，也会默认为写，只是limit下标+1
     */

    public static void main(String[] args) {
        ByteBuffer buffer = ByteBuffer.allocate(1024);

        System.out.println(String.format("写入前  capacity ：%s,limit:%s,position:%s" , buffer.capacity(), buffer.limit(), buffer.position()));

        buffer.put((byte) 1);
        buffer.put((byte) 2);
        buffer.put((byte) 3);
        System.out.println(String.format("写入后  capacity ：%s,limit:%s,position:%s" , buffer.capacity(), buffer.limit(), buffer.position()));

        System.out.println("读取数据");
        buffer.flip();//切换为读取模式
        System.out.println(buffer.get());

        System.out.println(String.format("读取一个字节数据后  capacity ：%s,limit:%s,position:%s" , buffer.capacity(), buffer.limit(), buffer.position()));

        buffer.compact();//切换为写入模式
        System.out.println("写模式");
        System.out.println(String.format("刚切换完 ：%s,limit:%s,position:%s" , buffer.capacity(), buffer.limit(), buffer.position()));

        buffer.put((byte) 4);
        buffer.put((byte) 5);
        buffer.put((byte) 6);
        System.out.println(String.format("写入三个数据后  capacity ：%s,limit:%s,position:%s" , buffer.capacity(), buffer.limit(), buffer.position()));

        buffer.flip();
        System.out.println("又切换为读模式");
        System.out.println(String.format("刚切换完 ：%s,limit:%s,position:%s" , buffer.capacity(), buffer.limit(), buffer.position()));
        System.out.println(buffer.get());
        System.out.println(buffer.get());
        System.out.println(buffer.get());
        System.out.println(String.format("读取三个数据后  capacity ：%s,limit:%s,position:%s" , buffer.capacity(), buffer.limit(), buffer.position()));

        buffer.compact();//再切换
        System.out.println(String.format("再次切换为写入模式  capacity ：%s,limit:%s,position:%s" , buffer.capacity(), buffer.limit(), buffer.position()));




    }
}
