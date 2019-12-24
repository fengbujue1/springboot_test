package com.zyj.springboot_test.test.java.serialization.model;

import com.dyuproject.protostuff.Tag;
import org.msgpack.annotation.Message;
import org.msgpack.annotation.MessagePackMessage;

import java.util.Random;

@MessagePackMessage
public class Numb {
    @Tag(1)
    private int i_1;
    @Tag(2)
    private int i_2;
    @Tag(3)
    private int i_3;
    @Tag(4)
    private int i_4;
    @Tag(5)
    private int i_5;
    @Tag(6)
    private int i_6;
    @Tag(7)
    private int i_7;
    @Tag(8)
    private int random;

    public void setI_1(int i_1) {
        this.i_1 = i_1;
    }

    public void setI_2(int i_2) {
        this.i_2 = i_2;
    }

    public void setI_3(int i_3) {
        this.i_3 = i_3;
    }

    public void setI_4(int i_4) {
        this.i_4 = i_4;
    }

    public void setI_5(int i_5) {
        this.i_5 = i_5;
    }

    public void setI_6(int i_6) {
        this.i_6 = i_6;
    }

    public void setI_7(int i_7) {
        this.i_7 = i_7;
    }

    public int getI_1() {

        return i_1;
    }

    public int getI_2() {
        return i_2;
    }

    public int getI_3() {
        return i_3;
    }

    public int getI_4() {
        return i_4;
    }

    public int getI_5() {
        return i_5;
    }

    public int getI_6() {
        return i_6;
    }

    public int getI_7() {
        return i_7;
    }

    public void setRandom(int random) {
        this.random = random;
    }

    public int getRandom() {

        return random;
    }

    public static Numb getInstance() {
        Numb numb = new Numb();
        numb.i_1 = 1;
        numb.i_2 = 1;
        numb.i_3 = 1;
        numb.i_4 = 1;
        numb.i_5 = 1;
        numb.i_6 = 1;
        numb.i_7 = 1;
//        numb.random = new Random().nextInt(1000);
        return numb;
    }
}
