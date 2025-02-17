package com.zyj.springboot_test.bean;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Objects;

//@ConfigurationProperties(prefix = "person")
//@Component
public class Person {
    private String name;
    private int age;
    private float height;
    private float weight;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public float getHeight() {
        return height;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    public float getWeight() {
        return weight;
    }

    public void setWeight(float weight) {
        this.weight = weight;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return age == person.age &&
                Float.compare(person.height, height) == 0 &&
                Float.compare(person.weight, weight) == 0 &&
                Objects.equals(name, person.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, age, height, weight);
    }

    public static void main(String[] args) {
        Person person = new Person();
        person.setAge(1);
        person.setName("name1");

        Person person2 = new Person();
        person2.setAge(1);
        person2.setName("name2");

        Person person3 = new Person();
        person3.setAge(1);
        person3.setName("name1");

        ArrayList<Person> people = new ArrayList<>();
        people.add(person);
        people.add(person2);
        people.add(person3);

        HashSet<Person> peopleSet = new HashSet<>();
        peopleSet.addAll(people);
        System.out.println(people.size());
        System.out.println(peopleSet.size());

    }
}
