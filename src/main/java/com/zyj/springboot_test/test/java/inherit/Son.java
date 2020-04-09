package com.zyj.springboot_test.test.java.inherit;

public class Son extends Father {
    public long son_1;

    public Son() {
        System.out.println("调用子类型无参构造器");
        father_1 = 1;
    }
    public Son(long father_2) {
        System.out.println("调用子类型有参构造器");
        this.father_2 = father_2;
    }
    public void reSet() {
        System.out.println("调用子类同名方法");
    }


    public static void main(String[] args) {
        //测试无参构造器的调用
//        Father sonInner = new Son();
//        System.out.println(sonInner.father_1);
//        System.out.println(sonInner.father_2);

        //测试有参构造器的调用
        Father sonInner2 = new Son(10);
//        System.out.println(sonInner2.father_2);

        //测试同名方法的调用
        sonInner2.reSet();
    }

}
