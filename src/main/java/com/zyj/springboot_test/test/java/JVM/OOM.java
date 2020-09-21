package com.zyj.springboot_test.test.java.JVM;

import java.util.ArrayList;

public class OOM {
    public static void main(String[] args) {
        ArrayList<Object> objects = new ArrayList<>();
        while (true) {
            objects.add(new Object());
        }

    }
}
