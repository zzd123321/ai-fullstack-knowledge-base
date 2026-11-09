package homeworks.homework1;

public class Homework1Enum {

    public enum PaymentStatus {
        PENDING(0, "待支付"),
        PAID(1, "已支付"),
        CANCELLED(2, "已取消"),
        REFUNDED(3, "已退款");

        private final int code;
        private final String desc;

        PaymentStatus(int code, String desc) {
            this.code = code;
            this.desc = desc;
        }

        public int getCode() {
            return code;
        }

        public String getDesc() {
            return desc;
        }

        public static PaymentStatus fromCode(int code) {
            for (PaymentStatus status : values()) {
                if (status.code == code) {
                    return status;
                }
            }
            return null;
        }
    }

    public static void main(String[] args) {
        System.out.println("所有支付状态：");
        for (PaymentStatus status : PaymentStatus.values()) {
            System.out.printf("code=%d, desc=%s%n", status.getCode(), status.getDesc());
        }

        // 测试 fromCode
        System.out.println("\nfromCode(1) -> " + PaymentStatus.fromCode(1));
        System.out.println("fromCode(99) -> " + PaymentStatus.fromCode(99));
    }
}