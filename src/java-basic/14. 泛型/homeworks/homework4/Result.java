package homework4;

/**
 * 作业四：泛型类 Result<T>，封装操作结果
 */
public class Result<T> {
    private T data;
    private String message;

    // 构造方法
    public Result(T data, String message) {
        this.data = data;
        this.message = message;
    }

    // Getter 和 Setter
    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    // 静态工厂方法：成功结果
    public static <T> Result<T> success(T data) {
        return new Result<>(data, "success");
    }

    // 静态工厂方法：失败结果
    public static <T> Result<T> error(String message) {
        return new Result<>(null, message);
    }

    @Override
    public String toString() {
        return "Result{data=" + data + ", message='" + message + "'}";
    }

    // 测试
    public static void main(String[] args) {
        Result<String> successResult = Result.success("数据加载完成");
        System.out.println(successResult);  // Result{data=数据加载完成, message='success'}

        Result<Integer> errorResult = Result.error("网络异常，请重试");
        System.out.println(errorResult);    // Result{data=null, message='网络异常，请重试'}

        // 演示获取数据
        if (successResult.getData() != null) {
            System.out.println("数据内容: " + successResult.getData());
        }
        if (errorResult.getData() == null) {
            System.out.println("错误信息: " + errorResult.getMessage());
        }
    }
}