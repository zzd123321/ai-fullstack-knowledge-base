import java.util.*;
import java.util.stream.*;

public class Homework2 {
    public static void main(String[] args) {
        List<Product> products = Arrays.asList(
            new Product("Electronics", 1200.0),
            new Product("Books", 25.0),
            new Product("Electronics", 800.0),
            new Product("Books", 35.0),
            new Product("Clothing", 50.0)
        );
        Map<String, Double> avgPrice = products.stream()
                .collect(Collectors.groupingBy(
                        Product::getCategory,
                        Collectors.averagingDouble(Product::getPrice)
                ));
        avgPrice.forEach((k, v) -> System.out.println(k + " -> " + v));
    }
}