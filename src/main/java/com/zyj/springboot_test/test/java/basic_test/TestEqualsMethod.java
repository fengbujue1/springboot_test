package com.zyj.springboot_test.test.java.basic_test;

public class TestEqualsMethod {
    public static void main(String[] args) {
        Student student = new Student("张三",1);
        Student student2 = new Student("张三",1);

        System.out.println(student.hashCode());
        System.out.println(student2.hashCode());
        System.out.println(student.equals(student2));
    }
}

class Student {
    String name;
    int num;

    public Student(String name, int num) {
        this.name = name;
        this.num = num;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Student) {
            return ((Student) obj).name == this.name && ((Student) obj).num == this.num;
        }
        return false;
    }
}
