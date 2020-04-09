package com.zyj.springboot_test.test.java.inherit;

/**
 * 实验：
 * 子类继承父类，重写无参构造器，子类实例以父类型的形式调用无参构造器，调用关系是怎么样的
 */
public class Father {
    public long father_1;
    public long father_2;

    public Father() {
        System.out.println("调用父类型无参构造器");
    }

    public Father(long father_2) {
        System.out.println("调用父类型有参构造器");
        this.father_2 = father_2;
    }


    public void reSet() {
        System.out.println("调用父类同名方法");
    }
}
