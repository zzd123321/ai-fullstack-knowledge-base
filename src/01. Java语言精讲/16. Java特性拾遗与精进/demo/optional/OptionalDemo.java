package demo.optional;

import java.util.Optional;

public class OptionalDemo {
    public static void main(String[] args) {
        // 1. isPresent() + get()（不推荐，等于传统的null检查）
        Optional<String> opt = Optional.ofNullable("hello");
        if (opt.isPresent()) {
            System.out.println(opt.get());
        }

        // 2. 推荐方式：函数式处理
        // ifPresent：存在则执行
        opt.ifPresent(value -> System.out.println("值: " + value));

        // 3. orElse：存在则返回原值，否则返回默认值
        String result = Optional.ofNullable(getName())
                .orElse("默认值");

        // 4. orElseGet：存在则返回原值，否则通过Supplier生成默认值
        String result2 = Optional.ofNullable(getName())
                .orElseGet(() -> "计算生成的默认值");

        // 5. orElseThrow：存在则返回原值，否则抛出异常
        String result3 = Optional.ofNullable(getName())
                .orElseThrow(() -> new RuntimeException("值不存在"));

        // 6. map：存在则转换，不存在则返回空Optional
        Optional<Integer> length = Optional.ofNullable("hello")
                .map(String::length);  // Optional[5]

        // 7. filter：存在且满足条件则返回自身，否则返回空Optional
        Optional<String> filtered = Optional.ofNullable("hello")
                .filter(s -> s.length() > 3);  // Optional[hello]

        // 8. flatMap：存在则通过函数返回Optional，否则返回空Optional
        Optional<Integer> result4 = Optional.ofNullable("123")
                .flatMap(s -> {
                    try {
                        return Optional.of(Integer.parseInt(s));
                    } catch (NumberFormatException e) {
                        return Optional.empty();
                    }
                });
    }

    private static String getName() {
        return null;  // 模拟可能为空
    }
}