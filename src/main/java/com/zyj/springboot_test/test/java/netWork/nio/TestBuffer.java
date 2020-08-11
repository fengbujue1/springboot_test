package com.zyj.springboot_test.test.java.netWork.nio;

import java.nio.Buffer;
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
 public static void testDirectBuffer() {
        //使用上和 堆内的内存没啥区别
        ByteBuffer directBuffer = ByteBuffer.allocateDirect(6);//申请一片大小为3的堆外内存


        System.out.println(String.format("初始情况下（申请了6个字节的空间）：capacity:%s,limit:%s,position:%s", directBuffer.capacity(), directBuffer.limit(), directBuffer.position()));
        directBuffer.put((byte) 1);
        directBuffer.put((byte) 2);
        directBuffer.put((byte) 3);
        System.out.println(String.format("插入3个数据后：capacity:%s,limit:%s,position:%s", directBuffer.capacity(), directBuffer.limit(), directBuffer.position()));
        directBuffer.flip();
        System.out.println(String.format("切换为读模式后：capacity:%s,limit:%s,position:%s", directBuffer.capacity(), directBuffer.limit(), directBuffer.position()));
        System.out.println(directBuffer.get());
        System.out.println(directBuffer.get());
        System.out.println(String.format("读取两个数据之后：capacity:%s,limit:%s,position:%s", directBuffer.capacity(), directBuffer.limit(), directBuffer.position()));

        directBuffer.compact();//没有被读取的数据会被推到最左边
        System.out.println(String.format("切换为写模式后：capacity:%s,limit:%s,position:%s", directBuffer.capacity(), directBuffer.limit(), directBuffer.position()));

//        directBuffer.array();//无法将直接内存转化为字节数组，会报错

        directBuffer.clear();//清空数据

        directBuffer.put((byte) 1);
        directBuffer.put((byte) 2);
        directBuffer.put((byte) 3);
        System.out.println(String.format("清空数据，再插入三个数据后：capacity:%s,limit:%s,position:%s", directBuffer.capacity(), directBuffer.limit(), directBuffer.position()));

        System.out.println(directBuffer.get());
        System.out.println(String.format("读取一个数据后：capacity:%s,limit:%s,position:%s", directBuffer.capacity(), directBuffer.limit(), directBuffer.position()));

        directBuffer.flip();
        System.out.println(directBuffer.get());
        directBuffer.mark();//标记一个位置
        System.out.println(String.format("再次读取一个数据后：capacity:%s,limit:%s,position:%s", directBuffer.capacity(), directBuffer.limit(), directBuffer.position()));

        directBuffer.reset();//重置position到上次标记的位置
        System.out.println(directBuffer.get());

        directBuffer.rewind();//重置position到0位置
        System.out.println(directBuffer.get());
        System.out.println(String.format("重置position后再读取一个后：capacity:%s,limit:%s,position:%s", directBuffer.capacity(), directBuffer.limit(), directBuffer.position()));






    }
}
