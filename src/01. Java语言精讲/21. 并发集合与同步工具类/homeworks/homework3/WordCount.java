package homeworks.homework3;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class WordCount {

    // 模拟5个线程处理的文本数据
    private static final List<String> TEXT_DATA = Arrays.asList(
            "apple banana apple orange grape",
            "banana orange orange apple banana",
            "grape apple banana grape grape",
            "orange apple grape banana orange",
            "apple apple orange banana grape"
    );

    public static void main(String[] args) throws InterruptedException {
        // 使用 ConcurrentHashMap 存储词频
        ConcurrentHashMap<String, Integer> wordCountMap = new ConcurrentHashMap<>();

        ExecutorService executor = Executors.newFixedThreadPool(5);

        // 提交5个任务，每个处理一段文本
        for (int i = 0; i < TEXT_DATA.size(); i++) {
            final String text = TEXT_DATA.get(i);
            executor.submit(() -> {
                String[] words = text.split("\\s+");
                for (String word : words) {
                    // 使用 merge 方法原子更新计数
                    wordCountMap.merge(word, 1, Integer::sum);
                    // 也可以使用 compute 方法：
                    // wordCountMap.compute(word, (k, v) -> (v == null) ? 1 : v + 1);
                }
                System.out.println(Thread.currentThread().getName() + " 处理完成：" + text);
            });
        }

        // 关闭线程池并等待所有任务完成
        executor.shutdown();
        executor.awaitTermination(5, TimeUnit.SECONDS);

        // 打印统计结果
        System.out.println("\n===== 词频统计结果 =====");
        wordCountMap.forEach((word, count) -> System.out.println(word + " : " + count));

        // 验证总单词数（可选）
        int totalWords = wordCountMap.values().stream().mapToInt(Integer::intValue).sum();
        System.out.println("总单词数：" + totalWords);
    }
}