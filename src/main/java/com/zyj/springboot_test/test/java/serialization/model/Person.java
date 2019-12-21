package com.zyj.springboot_test.test.java.serialization.model;

public class Person {
    private Person father;
    private Car car;
    private String name;
    private int age;

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

    public void setName(String name) {
        this.name = name;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getName() {

        return name;
    }

    public int getAge() {
        return age;
    }

    public static Person build() {
        //准备数据
        Engine engine = new Engine();
        Car car = new Car();
        car.setEngine(engine);
        Person father = new Person();
        father.setName("father");
        father.setAge(50);


        Person person = new Person();
        person.setAge(26);
        person.setName("son");
        person.setCar(car);
        person.setFather(father);
        return person;
    }
}
