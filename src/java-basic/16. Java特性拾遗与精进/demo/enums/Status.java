package demo.enums;

public enum Status {
    SUCCESS(0, "成功"),
    FAILURE(1, "失败"),
    PENDING(2, "处理中");

    private final int code;
    private final String message;

    Status(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() { return code; }
    public String getMessage() { return message; }

    public static Status fromCode(int code) {
        for (Status s : Status.values()) {
            if (s.code == code) return s;
        }
        throw new IllegalArgumentException("Unknown code: " + code);
    }
}