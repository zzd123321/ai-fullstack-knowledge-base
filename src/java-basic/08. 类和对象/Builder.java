public class Builder {
    private String value;

    public Builder setValue(String value) {
        this.value = value;
        return this;  // 返回当前对象，支持链式调用
    }

    public static void main(String[] args) {

        Builder b = new Builder().setValue("hello").setValue("world");
    }
}