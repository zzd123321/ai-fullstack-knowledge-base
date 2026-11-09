import java.util.*;

public class Homework1 {
    public static void main(String[] args) {
        List<Integer> nums = new ArrayList<>(Arrays.asList(3, 1, 4, 1, 5));
        List<Integer> copy = new ArrayList<>(nums);
        System.out.println(nums.equals(copy) + " " + (nums == copy));
        nums.set(0, 10);

        System.out.println(nums.get(0) + " " + copy.get(0));
        System.out.println(nums.equals(copy) + " " + (nums == copy));
        System.out.println(nums.size());

        int i = 0, count = 0;
        while (i < nums.size()) {
            if (nums.get(i) > 3) count++;
            i++;
        }
        System.out.println(count);
    }
}