import java.util.Arrays;

public class Homework1 {
    public static void change(int x, int[] arr) {
        x = 100;
        arr[0] = 999;
        arr = new int[]{1, 2, 3};
    }
    
    public static void print(int a, int b) {
    		System.out.println("int-int: " + a + ", " + b);
    }

    public static void print(Integer a, Integer b) {
        System.out.println("Integer-Integer: " + a + ", " + b);
    }

    public static void print(int... nums) {
        System.out.println("varargs: " + Arrays.toString(nums));
    }

    public static void main(String[] args) {
        int num = 5;
        int[] list = {10, 20, 30};
        change(num, list);
        System.out.println("num = " + num);
        System.out.println("list[0] = " + list[0]);

        print(1, 2);
        print(1, 2, 3);
    }
}