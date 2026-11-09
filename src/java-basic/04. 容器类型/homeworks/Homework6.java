import java.util.*;

/**
 * 作业六答案：图书信息管理综合练习
 */
public class Homework6 {
    public static void main(String[] args) {
        // ---------- 数据初始化 ----------
        List<Map<String, Object>> books = new ArrayList<>();

        // 直接添加每本书（不使用辅助方法）
        Map<String, Object> book1 = new HashMap<>();
        book1.put("id", 1001);
        book1.put("title", "Java核心技术");
        book1.put("author", "Cay S. Horstmann");
        book1.put("price", 89.0);
        book1.put("year", 2019);
        book1.put("category", "编程");
        books.add(book1);

        Map<String, Object> book2 = new HashMap<>();
        book2.put("id", 1002);
        book2.put("title", "三体");
        book2.put("author", "刘慈欣");
        book2.put("price", 93.0);
        book2.put("year", 2008);
        book2.put("category", "科幻");
        books.add(book2);

        Map<String, Object> book3 = new HashMap<>();
        book3.put("id", 1003);
        book3.put("title", "时间简史");
        book3.put("author", "霍金");
        book3.put("price", 45.0);
        book3.put("year", 2010);
        book3.put("category", "科学");
        books.add(book3);

        Map<String, Object> book4 = new HashMap<>();
        book4.put("id", 1004);
        book4.put("title", "Python编程从入门到实践");
        book4.put("author", "Eric Matthes");
        book4.put("price", 79.0);
        book4.put("year", 2016);
        book4.put("category", "编程");
        books.add(book4);

        Map<String, Object> book5 = new HashMap<>();
        book5.put("id", 1005);
        book5.put("title", "百年孤独");
        book5.put("author", "马尔克斯");
        book5.put("price", 55.0);
        book5.put("year", 2011);
        book5.put("category", "小说");
        books.add(book5);

        Map<String, Object> book6 = new HashMap<>();
        book6.put("id", 1006);
        book6.put("title", "算法导论");
        book6.put("author", "Thomas H. Cormen");
        book6.put("price", 128.0);
        book6.put("year", 2009);
        book6.put("category", "编程");
        books.add(book6);

        Map<String, Object> book7 = new HashMap<>();
        book7.put("id", 1007);
        book7.put("title", "人类简史");
        book7.put("author", "尤瓦尔·赫拉利");
        book7.put("price", 68.0);
        book7.put("year", 2014);
        book7.put("category", "历史");
        books.add(book7);

        Map<String, Object> book8 = new HashMap<>();
        book8.put("id", 1008);
        book8.put("title", "三体II：黑暗森林");
        book8.put("author", "刘慈欣");
        book8.put("price", 93.0);
        book8.put("year", 2008);
        book8.put("category", "科幻");
        books.add(book8);

        Map<String, Object> book9 = new HashMap<>();
        book9.put("id", 1009);
        book9.put("title", "三体III：死神永生");
        book9.put("author", "刘慈欣");
        book9.put("price", 93.0);
        book9.put("year", 2010);
        book9.put("category", "科幻");
        books.add(book9);

        Map<String, Object> book10 = new HashMap<>();
        book10.put("id", 1010);
        book10.put("title", "深入理解Java虚拟机");
        book10.put("author", "周志明");
        book10.put("price", 129.0);
        book10.put("year", 2019);
        book10.put("category", "编程");
        books.add(book10);

        Map<String, Object> book11 = new HashMap<>();
        book11.put("id", 1011);
        book11.put("title", "活着");
        book11.put("author", "余华");
        book11.put("price", 35.0);
        book11.put("year", 2012);
        book11.put("category", "小说");
        books.add(book11);

        Map<String, Object> book12 = new HashMap<>();
        book12.put("id", 1012);
        book12.put("title", "围城");
        book12.put("author", "钱钟书");
        book12.put("price", 39.0);
        book12.put("year", 2000);
        book12.put("category", "小说");
        books.add(book12);

        Map<String, Object> book13 = new HashMap<>();
        book13.put("id", 1013);
        book13.put("title", "万历十五年");
        book13.put("author", "黄仁宇");
        book13.put("price", 45.0);
        book13.put("year", 2006);
        book13.put("category", "历史");
        books.add(book13);

        Map<String, Object> book14 = new HashMap<>();
        book14.put("id", 1014);
        book14.put("title", "宇宙简史");
        book14.put("author", "霍金");
        book14.put("price", 38.0);
        book14.put("year", 2015);
        book14.put("category", "科学");
        books.add(book14);

        Map<String, Object> book15 = new HashMap<>();
        book15.put("id", 1015);
        book15.put("title", "数据结构与算法分析");
        book15.put("author", "Mark Allen Weiss");
        book15.put("price", 79.0);
        book15.put("year", 2012);
        book15.put("category", "编程");
        books.add(book15);

        Map<String, Object> book16 = new HashMap<>();
        book16.put("id", 1016);
        book16.put("title", "人性的弱点");
        book16.put("author", "戴尔·卡耐基");
        book16.put("price", 32.0);
        book16.put("year", 2018);
        book16.put("category", "心理");
        books.add(book16);

        Map<String, Object> book17 = new HashMap<>();
        book17.put("id", 1017);
        book17.put("title", "富爸爸穷爸爸");
        book17.put("author", "罗伯特·清崎");
        book17.put("price", 39.0);
        book17.put("year", 2000);
        book17.put("category", "经管");
        books.add(book17);

        Map<String, Object> book18 = new HashMap<>();
        book18.put("id", 1018);
        book18.put("title", "经济学原理");
        book18.put("author", "曼昆");
        book18.put("price", 88.0);
        book18.put("year", 2015);
        book18.put("category", "经管");
        books.add(book18);

        Map<String, Object> book19 = new HashMap<>();
        book19.put("id", 1019);
        book19.put("title", "香水");
        book19.put("author", "帕特里克·聚斯金德");
        book19.put("price", 42.0);
        book19.put("year", 2010);
        book19.put("category", "小说");
        books.add(book19);

        Map<String, Object> book20 = new HashMap<>();
        book20.put("id", 1020);
        book20.put("title", "我的前半生");
        book20.put("author", "溥仪");
        book20.put("price", 55.0);
        book20.put("year", 2007);
        book20.put("category", "历史");
        books.add(book20);

        Map<String, Object> book21 = new HashMap<>();
        book21.put("id", 1021);
        book21.put("title", "流浪地球");
        book21.put("author", "刘慈欣");
        book21.put("price", 35.0);
        book21.put("year", 2012);
        book21.put("category", "科幻");
        books.add(book21);

        Map<String, Object> book22 = new HashMap<>();
        book22.put("id", 1022);
        book22.put("title", "微积分学教程");
        book22.put("author", "菲赫金哥尔茨");
        book22.put("price", 150.0);
        book22.put("year", 2014);
        book22.put("category", "科学");
        books.add(book22);

        // ---------- 任务 1：遍历输出书名和价格 ----------
        System.out.println("=== 任务1：遍历输出书名和价格 ===");
        for (Map<String, Object> book : books) {
            String title = (String) book.get("title");
            double price = (double) book.get("price");
            System.out.printf("《%s》 价格: ¥%.2f\n", title, price);
        }

        // ---------- 任务 2：所有编程类图书 ----------
        System.out.println("\n=== 任务2：所有编程类图书 ===");
        List<Map<String, Object>> programmingBooks = new ArrayList<>();
        for (Map<String, Object> book : books) {
            if ("编程".equals(book.get("category"))) {
                programmingBooks.add(book);
            }
        }
        System.out.println("编程类图书数量: " + programmingBooks.size());
        for (Map<String, Object> b : programmingBooks) {
            System.out.println(b.get("title"));
        }

        // ---------- 任务 3：2015年之后出版的图书 ----------
        System.out.println("\n=== 任务3：2015年后出版的图书 ===");
        List<Map<String, Object>> recentBooks = new ArrayList<>();
        for (Map<String, Object> book : books) {
            int year = (int) book.get("year");
            if (year > 2015) {
                recentBooks.add(book);
            }
        }
        System.out.println("2015年后出版数量: " + recentBooks.size());
        for (Map<String, Object> b : recentBooks) {
            System.out.println(b.get("title") + " (" + b.get("year") + ")");
        }

        // ---------- 任务 4：价格低于50元的图书 ----------
        System.out.println("\n=== 任务4：价格低于50元的图书 ===");
        List<Map<String, Object>> cheapBooks = new ArrayList<>();
        for (Map<String, Object> book : books) {
            double price = (double) book.get("price");
            if (price < 50) {
                cheapBooks.add(book);
            }
        }
        System.out.println("低价书数量: " + cheapBooks.size());
        for (Map<String, Object> b : cheapBooks) {
            System.out.println(b.get("title") + " ¥" + b.get("price"));
        }

        // ---------- 任务 5：作者为"刘慈欣"的图书 ----------
        System.out.println("\n=== 任务5：作者为'刘慈欣'的图书 ===");
        List<Map<String, Object>> authorBooks = new ArrayList<>();
        for (Map<String, Object> book : books) {
            if ("刘慈欣".equals(book.get("author"))) {
                authorBooks.add(book);
            }
        }
        System.out.println("刘慈欣作品数量: " + authorBooks.size());
        for (Map<String, Object> b : authorBooks) {
            System.out.println(b.get("title"));
        }

        // ---------- 任务 6：所有书名列表 ----------
        System.out.println("\n=== 任务6：所有书名列表 ===");
        List<String> titles = new ArrayList<>();
        for (Map<String, Object> book : books) {
            titles.add((String) book.get("title"));
        }
        System.out.println(titles);

        // ---------- 任务 7：价格高于80元的书名列表 ----------
        System.out.println("\n=== 任务7：价格高于80元的书名列表 ===");
        List<String> expensiveTitles = new ArrayList<>();
        for (Map<String, Object> book : books) {
            double price = (double) book.get("price");
            if (price > 80) {
                expensiveTitles.add((String) book.get("title"));
            }
        }
        System.out.println(expensiveTitles);

        // ---------- 任务 8：所有图书摘要（仅title和price） ----------
        System.out.println("\n=== 任务8：所有图书摘要（title+price） ===");
        List<Map<String, Object>> bookSummaries = new ArrayList<>();
        for (Map<String, Object> book : books) {
            Map<String, Object> summary = new HashMap<>();
            summary.put("title", book.get("title"));
            summary.put("price", book.get("price"));
            bookSummaries.add(summary);
        }
        System.out.println("摘要数量: " + bookSummaries.size());
        for (Map<String, Object> s : bookSummaries) {
            System.out.println(s.get("title") + " -> " + s.get("price"));
        }

        // ---------- 任务 9：总价格 ----------
        System.out.println("\n=== 任务9：总价格 ===");
        double totalPrice = 0;
        for (Map<String, Object> book : books) {
            totalPrice += (double) book.get("price");
        }
        System.out.printf("总价格: ¥%.2f\n", totalPrice);

        // ---------- 任务 10：平均价格 ----------
        System.out.println("\n=== 任务10：平均价格 ===");
        double avgPrice = totalPrice / books.size();
        System.out.printf("平均价格: ¥%.2f\n", avgPrice);

        // ---------- 任务 11：按类别分组（category -> list of titles） ----------
        System.out.println("\n=== 任务11：按类别分组 ===");
        Map<String, List<String>> categoryMap = new HashMap<>();
        for (Map<String, Object> book : books) {
            String category = (String) book.get("category");
            String title = (String) book.get("title");
            if (!categoryMap.containsKey(category)) {
                categoryMap.put(category, new ArrayList<>());
            }
            categoryMap.get(category).add(title);
        }
        for (Map.Entry<String, List<String>> entry : categoryMap.entrySet()) {
            System.out.println(entry.getKey() + " -> " + entry.getValue());
        }

        // ---------- 任务 12：查找 id 为 1024 的图书 ----------
        System.out.println("\n=== 任务12：查找id为1024的图书 ===");
        boolean found1024 = false;
        for (Map<String, Object> book : books) {
            int id = (int) book.get("id");
            if (id == 1024) {
                System.out.println("找到图书: " + book.get("title") + ", 作者: " + book.get("author"));
                found1024 = true;
                break;
            }
        }
        if (!found1024) {
            System.out.println("未找到该图书");
        }

        // ---------- 任务 13：是否存在价格大于150元的图书 ----------
        System.out.println("\n=== 任务13：是否存在价格大于150元的图书 ===");
        boolean hasExpensive = false;
        for (Map<String, Object> book : books) {
            double price = (double) book.get("price");
            if (price > 150) {
                System.out.println("true, 图书: " + book.get("title") + " 价格: " + price);
                hasExpensive = true;
                break;
            }
        }
        if (!hasExpensive) {
            System.out.println("false");
        }

        // ---------- 任务 14：是否所有图书价格都低于200元 ----------
        System.out.println("\n=== 任务14：是否所有图书价格都低于200元 ===");
        boolean allBelow200 = true;
        for (Map<String, Object> book : books) {
            double price = (double) book.get("price");
            if (price >= 200) {
                allBelow200 = false;
                break;
            }
        }
        System.out.println(allBelow200);
    }
}