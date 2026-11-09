package homework3;

/**
 * 自定义检查异常：分数不合法异常
 */
class InvalidScoreException extends Exception {
    public InvalidScoreException(String message) {
        super(message);
    }
}