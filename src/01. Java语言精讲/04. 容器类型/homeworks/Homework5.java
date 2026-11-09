import java.util.*;

public class Homework5 {
    public static void main(String[] args) {
        List<String> names = Arrays.asList("Alice", "Bob");
        List<Integer> ages = Arrays.asList(25, 30);

        List<String> pairs = new ArrayList<>();
        for (int i = 0; i < names.size(); i++) {
            pairs.add(names.get(i) + "-" + ages.get(i));
        }
        System.out.println(pairs);

        // 模拟 enumerate
        int idx = 1;
        for (String name : names) {
            System.out.println(idx + " " + name);
            idx++;
        }

        Map<String, Integer> d = new HashMap<>();
        for (int i = 0; i < names.size(); i++) {
            if (ages.get(i) > 20) {
                d.put(names.get(i), ages.get(i));
            }
        }
        System.out.println(d.size());
        System.out.println(d.getOrDefault("Alice", "not found"));
        System.out.println(d.getOrDefault("Charlie", "not found"));
    }
}