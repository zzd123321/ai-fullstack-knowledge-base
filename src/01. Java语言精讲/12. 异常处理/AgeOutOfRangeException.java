public class AgeOutOfRangeException extends Exception {
    public AgeOutOfRangeException() {
        super("年龄超出合法范围");
    }

    public AgeOutOfRangeException(String message) {
        super(message);
    }
}