package testIOC;

import testDI.model.Money;

public class BoyFactory {
    public static Boy getBean(String name, Money m) {
        return new Lad(name, m);
    }
}
