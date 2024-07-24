package com.chenmeng.common.utils;

import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;

/**
 * @author chenmeng
 */
public class StringUtil {

    /**
     * 内置关键词，用于生成appSecret
     */
    private final static String KEY_WORD = "easy-web-cloud";

    /**
     * 生成唯一appKey，基于无短横线的UUID。
     *
     * @return 唯一的appKey
     */
    public static String generateAppKey() {
        return IdUtil.simpleUUID();
    }

    /**
     * 生成appSecret，结合appKey和预定义关键字通过SHA256散列。
     *
     * @param appKey 应用的唯一标识
     * @return 生成的appSecret
     * @throws IllegalArgumentException 如果appKey为空
     */
    public static String generateAppSecret(String appKey) {
        if (StrUtil.isBlank(appKey)) {
            throw new IllegalArgumentException("appKey cannot be blank.");
        }
        // 组合appKey和keyWord
        String source = appKey + KEY_WORD;
        // 使用SHA256生成散列值
        return SecureUtil.sha256(source);
    }

    /**
     * 将字符串转换为小写。
     *
     * @param str 待转换的字符串
     * @return 转换为小写后的字符串
     */
    public static String toLowerCase(String str) {
        return str == null ? null : str.toLowerCase();
    }

    /**
     * 将字符串转换为大写。
     *
     * @param str 待转换的字符串
     * @return 转换为大写后的字符串
     */
    public static String toUpperCase(String str) {
        return str == null ? null : str.toUpperCase();
    }

    /**
     * 判断字符串是否全部由数字组成。
     *
     * @param str 待检查的字符串
     * @return 如果字符串全部由数字组成，返回true，否则返回false。
     */
    public static boolean isNumeric(String str) {
        if (str == null) {
            return false;
        }
        for (char c : str.toCharArray()) {
            if (!Character.isDigit(c)) {
                return false;
            }
        }
        return true;
    }

    /**
     * 判断字符串是否包含中文字符。
     *
     * @param str 待检查的字符串
     * @return 如果字符串包含中文字符，返回true，否则返回false。
     */

    @SuppressWarnings("UnnecessaryUnicodeEscape")
    public static boolean containsChinese(String str) {
        if (str == null) {
            return false;
        }
        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            // 中文字符的Unicode编码范围是\u4e00到\u9fa5
            if (c >= '一' && c <= '龥') {
                return true;
            }
        }
        return false;
        // return str.matches(".*[\u4e00-\u9fa5]+.*");
    }

    public static void main(String[] args) {
        // 示例
        String testStr = "  Hello, World!  ";
        String numericStr = "12345";
        String chineseStr = "你好，世界";

        System.out.println("生成AppKey(8) = " + generateAppKey());
        System.out.println("生成AppSecret = " + generateAppSecret(generateAppKey()));
        System.out.println("转换为小写: " + toLowerCase(testStr));
        System.out.println("转换为大写: " + toUpperCase(testStr));
        System.out.println("是否全部由数字组成: " + isNumeric(numericStr));
        System.out.println("是否包含中文字符: " + containsChinese(chineseStr));

        /*
            生成AppKey(8) = HEXF7zay
            生成AppSecret = c089204a77f1130d452b246adf3a576050851854423bb73e85ded851966acadb
            转换为小写:   hello, world!
            转换为大写:   HELLO, WORLD!
            是否全部由数字组成: true
            是否包含中文字符: true
        */
    }
}
