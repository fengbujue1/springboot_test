package testIOC;

public class BoyFactory {
    public static Boy getBoy() {
        return new Lad();
    }
}
