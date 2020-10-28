package com.zyj.springboot_test.test.java.basic_test;

public class WhatIsInteger {
    public static void main(String[] args) {

        Integer integer = new Integer(20);
        Integer integer1 = new Integer(20);
        System.out.println(integer == integer1);

        System.out.println(integer.hashCode());
        System.out.println(integer1.hashCode());
        System.out.println(integer.hashCode()==integer1.hashCode());

    }
}
