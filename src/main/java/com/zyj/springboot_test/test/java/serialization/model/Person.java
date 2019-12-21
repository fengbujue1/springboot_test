package com.zyj.springboot_test.test.java.serialization.model;

public class Person {
    private Person father;
    private Car car;

    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
    }

    public void setFather(Person father) {
        this.father = father;
    }

    public Person getFather() {

        return father;
    }

    public static Person build() {
        //准备数据
        Engine engine = new Engine();
        Car car = new Car();
        car.setEngine(engine);
        Person father = new Person();


        Person person = new Person();
        person.setCar(car);
        person.setFather(father);
        return person;
    }
}
