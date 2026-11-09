package homeworks.homework2;

public class Homework2TextBlock {

    public static void main(String[] args) {
        String userName = "张三";
        double balance = 1000.00;

        String html = """
                <html>
                    <head>
                        <title>欢迎页面</title>
                    </head>
                    <body>
                        <h1>欢迎, %s</h1>
                        <p>您的账户余额: %.2f 元</p>
                    </body>
                </html>
                """.formatted(userName, balance);

        System.out.println(html);
    }
}