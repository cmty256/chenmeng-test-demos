package com.chenmeng.common.utils;

/**
 * 脱敏工具类
 *
 * @author chenmeng
 */
public class DesensitizationUtil {

    private static final int TWO = 2;
    private static final int SIX = 6;
    private static final int SEVEN = 7;
    public static final String EMAIL_AT_SYMBOL = "@";

    /**
     * 字符串脱敏（脱敏规则：将指定范围内的字符替换为掩码字符）
     *
     * @param str      需要脱敏的原始字符串。
     * @param start    脱敏开始的位置（包含该位置的字符）。
     * @param end      脱敏结束的位置（不包含该位置的字符）。
     * @param maskChar 用于替换指定范围内字符的掩码字符。
     * @return 脱敏后的字符串。如果原始字符串为空或者脱敏范围无效，则返回原始字符串。
     */
    public static String mask(String str, int start, int end, char maskChar) {
        if (str == null || str.isEmpty()) {
            return str;
        }
        char[] chars = str.toCharArray();
        for (int i = start; i < end && i < chars.length; i++) {
            chars[i] = maskChar;
        }
        return new String(chars);
    }

    /**
     * 脱敏名称
     *
     * @param name      需要脱敏的原始字符串。
     * @return 脱敏后的字符串。如果原始字符串为空或者脱敏范围无效，则返回原始字符串。
     */
    public static String desensitizeName(String name) {
        if (name == null || name.isEmpty()) {
            return name;
        }
        int length = name.length();
        if (length <= TWO) {
            // 如果名称长度小于等于2，则只保留第一个字符，第二个字符替换为*
            return name.charAt(0) + "*";
        }
        char[] chars = name.toCharArray();
        for (int i = 1; i < name.length() - 1; i++) {
            chars[i] = '*';
        }
        return new String(chars);
    }

    /**
     * 脱敏电话号码，保留前三位和最后四位，其余替换为*
     * @param phone 待脱敏的电话号码
     * @return 脱敏后的电话号码
     */
    public static String desensitizePhone(String phone) {
        if (phone == null || phone.length() < SEVEN) {
            return phone;
        }
        int length = phone.length();
        return new StringBuilder(phone).replace(3, length - 4, "****").toString();
    }

    /**
     * 脱敏邮箱地址，保留第一个字符和@及以后的地址，其余替换为*
     * @param email 待脱敏的邮箱地址
     * @return 脱敏后的邮箱地址
     */
    public static String desensitizeEmail(String email) {
        if (email == null || !email.contains(EMAIL_AT_SYMBOL)) {
            return email;
        }
        String[] parts = email.split("@");
        if (parts.length != TWO) {
            return email;
        }
        int usernameLength = parts[0].length();
        return new StringBuilder(parts[0]).replace(1, usernameLength, "*****").append("@").append(parts[1]).toString();
    }

    /**
     * 脱敏身份证号码，保留前六位和最后四位，其余替换为******
     * @param idCard 待脱敏的身份证号码
     * @return 脱敏后的身份证号码
     */
    public static String desensitizeIdCard(String idCard) {
        if (idCard == null || idCard.length() < SIX) {
            return idCard;
        }
        int length = idCard.length();
        return new StringBuilder(idCard).replace(6, length - 4, "******").toString();
    }

    /**
     * 脱敏字符串中的所有数字，替换为*
     * @param str 待脱敏的字符串
     * @return 脱敏后的字符串
     */
    public static String desensitizeNumbers(String str) {
        if (str == null) {
            return null;
        }
        return str.replaceAll("\\d", "*");
    }

    public static void main(String[] args) {
        // 示例
        String name1 = "张三";
        String name2 = "张三丰";
        String phone = "13812345678";
        String email = "user@example.com";
        String idCard = "123456789012345678";
        String numberStr = "这里有一些数字123和一些文本abc";

        System.out.println("脱敏后的名称: " + desensitizeName(name1));
        System.out.println("脱敏后的名称: " + desensitizeName(name2));
        System.out.println("脱敏后的电话号码: " + mask(phone, 3, 7, '*'));
        System.out.println("脱敏后的电话号码: " + desensitizePhone(phone));
        System.out.println("脱敏后的邮箱地址: " + desensitizeEmail(email));
        System.out.println("脱敏后的身份证号码: " + desensitizeIdCard(idCard));
        System.out.println("脱敏后的字符串: " + desensitizeNumbers(numberStr));

        /*
            脱敏后的名称: 张*
            脱敏后的名称: 张*丰
            脱敏后的电话号码: 138****5678
            脱敏后的电话号码: 138****5678
            脱敏后的邮箱地址: u*****@example.com
            脱敏后的身份证号码: 123456******5678
            脱敏后的字符串: 这里有一些数字***和一些文本abc
        */
    }
}