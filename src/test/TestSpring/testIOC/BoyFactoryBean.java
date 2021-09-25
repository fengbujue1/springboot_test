package TestSpring.testIOC;

import TestSpring.testDI.model.Gril;

public class BoyFactoryBean {
    public Boy getBoy(String name, Gril g) {
        return new Lad(name , g);
    }
}
