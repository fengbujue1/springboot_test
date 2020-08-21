package com.zyj.springboot_test.test.java.singleton;

public class StaticCar {
    private StaticCar(){
    }

    /**
     * 静态内部类,通过JVM在加载类的时候就完成了初始化，并发控制由JVM控制
     */
    private static volatile StaticCar car;


    public static StaticCar getCar() {
        return InstanceHolder.instance;
    }


    private static class InstanceHolder {
        private static final StaticCar instance = new StaticCar();
    }
}
