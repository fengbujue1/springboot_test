package com.zyj.springboot_test.test.java.basic_test.collection;

import java.util.ArrayList;
import java.util.Comparator;

public class TestSortMethod {
   static class Person {
        public Person(String name, int age) {
            this.name = name;
            this.age = age;
        }

        String name;
        int age;

       @Override
       public String toString() {
           return "Person{" +
                   "name='" + name + '\'' +
                   ", age=" + age +
                   '}';
       }
   }
    public static void main(String[] args) {
        ArrayList<Person> people = new ArrayList<>();
        people.add(new Person("sss", 5));
        people.add(new Person("asd", 2));
        people.add(new Person("aaa", 3));

        System.out.println(people);

        people.sort(new Comparator<Person>() {
            @Override
            public int compare(Person o1, Person o2) {
                if (o1.age < o2.age) {
                    return 1;
                }
                return -1;
            }
        });
        System.out.println(people);
    }

}
