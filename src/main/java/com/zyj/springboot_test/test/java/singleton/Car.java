package com.zyj.springboot_test.test.java.singleton;

public class Car {
    private Car(){
    }

    private static volatile Car car;

    public static Car getCar() {
        if (car == null) {
            synchronized (Car.class) {
                if (car == null) {
                    car = new Car();
                }
            }
        }
        return car;
    }
}
