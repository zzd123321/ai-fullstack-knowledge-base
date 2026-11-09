import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public class Demo {


    public static void main(String[] args) {

        int sum = Stream.of(1, 2, 3, 4)
                .reduce(5, Integer::sum);
        System.out.println(sum);


        List<String> words = Arrays.asList("hello", "world");
        words.stream()
                .map(String::toUpperCase)
                .forEach(System.out::println); // HELLO, WORLD


        List<List<Integer>> nested = Arrays.asList(
                Arrays.asList(1, 2),
                Arrays.asList(3, 4),
                Arrays.asList(5, 6)
        );
        nested.stream()
                .flatMap(List::stream)   // 扁平化
                .forEach(System.out::println); // 1,2,3,4,5,6

        // 拆分词为字母
        String[] wordArrays = {"hello", "world"};
        Arrays.stream(wordArrays)
                .flatMap(w -> Arrays.stream(w.split("")))
                .forEach(System.out::println); // h,e,l,l,o,w,o,r,l,d
    }

}