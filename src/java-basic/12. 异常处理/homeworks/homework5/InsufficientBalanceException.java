package homework5;

/**
 * 自定义非检查异常：余额不足异常
 */
class InsufficientBalanceException extends RuntimeException {
    public InsufficientBalanceException(String message) {
        super(message);
    }
}