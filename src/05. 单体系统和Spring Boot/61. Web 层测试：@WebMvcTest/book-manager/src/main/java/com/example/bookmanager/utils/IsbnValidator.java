package com.example.bookmanager.utils;

/**
 * ISBN 校验工具类
 * 只校验格式，不校验 ISBN 校验位。
 */
public class IsbnValidator {

    /**
     * 去除 ISBN 中的连字符和空白字符
     */
    private static String clean(String isbn) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < isbn.length(); i++) {
            char c = isbn.charAt(i);
            if (c != '-' && !Character.isWhitespace(c)) {
                sb.append(c);
            }
        }
        return sb.toString();
    }

    /**
     * 校验 ISBN 格式是否合法
     */
    public static boolean isValid(String isbn) {
        if (isbn == null || isbn.isBlank()) {
            return false;
        }

        String cleaned = clean(isbn);

        if (cleaned.length() == 10) {
            // ISBN-10：前 9 位必须是数字，最后一位是数字或 X/x
            for (int i = 0; i < 9; i++) {
                if (!Character.isDigit(cleaned.charAt(i))) {
                    return false;
                }
            }
            char last = cleaned.charAt(9);
            return Character.isDigit(last) || last == 'X' || last == 'x';
        }

        if (cleaned.length() == 13) {
            // ISBN-13：13 位全是数字，且以 978 或 979 开头
            if (!cleaned.startsWith("978") && !cleaned.startsWith("979")) {
                return false;
            }
            for (int i = 0; i < 13; i++) {
                if (!Character.isDigit(cleaned.charAt(i))) {
                    return false;
                }
            }
            return true;
        }

        return false;
    }

    /**
     * 获取 ISBN 类型
     */
    public static String getType(String isbn) {
        if (!isValid(isbn)) {
            throw new IllegalArgumentException("无效的 ISBN: " + isbn);
        }
        String cleaned = clean(isbn);
        return cleaned.length() == 10 ? "ISBN-10" : "ISBN-13";
    }

    /**
     * 标准化 ISBN（去除连字符和空格）
     */
    public static String normalize(String isbn) {
        if (isbn == null) {
            throw new IllegalArgumentException("ISBN 不能为 null");
        }
        return clean(isbn);
    }
}