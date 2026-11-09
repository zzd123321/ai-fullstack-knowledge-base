
@FunctionalInterface
public interface Calculator {
    int calculate(int a, int b);
    // int another();  // 编译错误：函数式接口只能有一个抽象方法
}