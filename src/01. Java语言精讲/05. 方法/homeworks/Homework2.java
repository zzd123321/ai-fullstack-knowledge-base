import java.util.Arrays;

public class Homework2 {
    public static void swap(int a, int b) {
        int temp = a;
        a = b;
        b = temp;
    }

    public static void swapArray(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    public static void main(String[] args) {
        int x = 10, y = 20;
        swap(x, y);
        System.out.println("x=" + x + ", y=" + y);

        int[] nums = {1, 2, 3};
        swapArray(nums, 0, 2);
        System.out.println(Arrays.toString(nums));
    }
}