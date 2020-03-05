package com.zyj.springboot_test.test.tomcat;

public class TestPath {
    public static void main(String[] args) {
        String path = TestPath.class.getResource("/").getPath();
        System.out.println(path);
    }
}
