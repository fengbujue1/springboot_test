package com.zyj.springboot_test.test.java.designMode.observe;

import java.util.Observable;
import java.util.Observer;

public class TestObserve {
    public static void main(String[] args){
        Observable observable = new Observable(){
            @Override
            public void notifyObservers(Object arg) {
                super.setChanged();
                super.notifyObservers(arg);
            }
        };

        Observer observer1 = new Observer() {
            @Override
            public void update(Observable o, Object arg) {
                System.out.println("chamnge:" + this);
                System.out.println("msg:" + arg);
            }
        };
        Observer observer2 = new Observer() {
            @Override
            public void update(Observable o, Object arg) {
                System.out.println("chamnge:" + this);
                System.out.println("msg:" + arg);
            }
        };

        observable.addObserver(observer1);
        observable.addObserver(observer2);

        observable.notifyObservers("asdd");
    }
}
