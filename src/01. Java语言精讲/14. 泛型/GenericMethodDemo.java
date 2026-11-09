import java.util.Arrays;

public class GenericMethodDemo {
    // 泛型方法：交换数组中的两个元素
    public static <T> void swap(T[] array, int i, int j) {
        T temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }

    // 泛型方法：返回泛型数组中的第一个元素
    public static <T> T getFirst(T[] array) {
        if (array == null || array.length == 0) {
            return null;
        }
        return array[0];
    }

    public static void main(String[] args) {
        String[] words = {"Hello", "World", "!"};
        swap(words, 0, 1);
        System.out.println(Arrays.toString(words)); // [World, Hello, !]

        Integer[] nums = {10, 20, 30};
        Integer first = getFirst(nums);
        System.out.println(first); // 10
    }
}