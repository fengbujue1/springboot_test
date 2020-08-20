package com.zyj.springboot_test.test.java.singleton;

public class Car {
    private Car(){
    }

    /**
     * volatile关键字
     * 1.保证线程间这个对象的可见性，
     * 2.最主要的是禁止指令重排序，因为new一个对象的过程不是原子性的，且各个操作之间没有明显的依赖，会导致引用先被赋予，但是实例还没有完成初始化
     * 导致其他线程（17行跳到24行）取到空值。
     */
    private static volatile Car car;


    public static Car getCar() {
        if (car == null) {
            synchronized (Car.class) {//同步块中使用再次进行检测，你面并发情况下发生的对象引用覆盖
                if (car == null) {
                    car = new Car();
                }
            }
        }
        return car;
    }
}
