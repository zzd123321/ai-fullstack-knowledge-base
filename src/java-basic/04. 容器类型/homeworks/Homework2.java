import java.util.*;

public class Homework2 {
    public static void main(String[] args) {
        List<Integer> t = Arrays.asList(5, 2, 8, 2);
        Set<Integer> s = new HashSet<>(t);
        System.out.println(s.size());
        System.out.println(s.contains(2) + " " + !s.contains(9));

        Set<Integer> s2 = new HashSet<>(Arrays.asList(2, 8, 10));
        Set<Integer> inter = new HashSet<>(s);
        inter.retainAll(s2);
        Set<Integer> union = new HashSet<>(s);
        union.addAll(s2);
        System.out.println(inter + " " + union);
    }
}