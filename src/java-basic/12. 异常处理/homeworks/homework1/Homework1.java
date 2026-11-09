package homework1;

public class Homework1 {
    public static void main(String[] args) {
        try {
            int[] arr = new int[3];
            arr[5] = 10;
            int result = 10 / 0;
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("捕获到数组越界");
        } catch (ArithmeticException e) {
            System.out.println("捕获到算术异常");
        } catch (Exception e) {
            System.out.println("捕获到通用异常");
        } finally {
            System.out.println("finally 执行");
        }
    }
}