import java.util.*;

public class Homework3 {
    public static void main(String[] args) {
        Map<String, Integer> d = new HashMap<>();
        d.put("x", 10);
        d.put("y", 20);
        d.put("z", 30);
        d.put("x", 15);
        System.out.println(d.size());
        System.out.println(d.containsKey("x") + " " + d.containsValue(20));

        System.out.println(d.getOrDefault("w", 0));

        List<String> keys = new ArrayList<>();
        for (String k : d.keySet()) {
            keys.add(k);
        }
        System.out.println(keys);

        int i = 0;
        while (i < keys.size()) {
            String k = keys.get(i);
            if (d.get(k) > 15) {
                System.out.println(k);
            }
            i++;
        }
    }
}