package TestSpring.testIOC;

import TestSpring.testDI.model.Gril;
import TestSpring.testDI.model.MagicGril;
import TestSpring.testDI.model.Money;

public class Lad implements Boy {
    private String name;
    private Gril friend;
    private Money money;

    public Lad() {
    }

    public Lad(String name) {
        this.name = name;
    }

    public Lad(String name, MagicGril gf) {
        this.name = name;
        this.friend = gf;
        System.out.println("调用了含有MagicGril参数的构造方法");
    }
    public Lad(String name, Gril gf) {
        this.name = name;
        this.friend = gf;
        System.out.println("调用了含有Gril参数的构造方法");
    }
    public Lad(String name, Money m) {
        this.name = name;
        this.money = m;
        System.out.println("调用了含有Money参数的构造方法");
    }

    public Gril getFriend() {
        return friend;
    }

    public void setFriend(Gril friend) {
        this.friend = friend;
    }


    public void sayLove() {
        System.out.println(toString());;
        System.out.println("love    " + hashCode());
        System.out.println("myGirl    " + this.friend);

    }

    @Override
    public void play() {
        if(money != null) {
            System.out.println("I have money, let's play.");
        }else{
            System.out.println("I have no money, can't play.");
        }
    }

    public void init() {
        System.out.println("start init");
    }
    public void destroy() {
        System.out.println("destroy ");
    }

    @Override
    public String toString() {
        return "Lad{" +
                "name='" + name + '\'' +
                ", friend=" + friend +
                ", money=" + money +
                '}';
    }
}
