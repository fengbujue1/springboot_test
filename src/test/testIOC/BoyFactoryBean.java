package testIOC;

public class BoyFactoryBean {
    public Boy getBoy() {
        return new Lad();
    }
}
