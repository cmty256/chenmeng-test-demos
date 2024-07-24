package com.chenmeng.common.utils;

/**
 * 正则工具类
 *
 * @author chenmeng
 */
public class RegexUtil {

    /**
     * 检查字符串是否为有效的邮箱地址。
     *
     * @param email 待验证的邮箱地址字符串
     * @return 邮箱地址有效返回 true，否则返回 false
     */
    public static boolean isEmail(String email) {
        // 邮箱地址的正则表达式，匹配常见的邮箱格式，包括用户名部分、@符号、域名部分和顶级域名
        String emailRegex = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$";
        return matches(email, emailRegex);
    }

    /**
     * 检查字符串是否为有效的手机号码（中国大陆）。
     *
     * @param mobile 待验证的手机号码字符串
     * @return 手机号码有效返回 true，否则返回 false
     */
    public static boolean isMobile(String mobile) {
        // 手机号码的正则表达式，匹配中国大陆的手机号格式，以1开头，紧跟10位数字
        String mobileRegex = "^1[3-9]\\d{9}$";
        return matches(mobile, mobileRegex);
    }

    /**
     * 检查字符串是否为有效的身份证号码。
     *
     * @param idCard 待验证的身份证号码字符串
     * @return 身份证号码有效返回 true，否则返回 false
     */
    public static boolean isIdCard(String idCard) {
        // 身份证号码的正则表达式，匹配中国大陆的身份证号码格式，18位数字或17位数字加最后一位为X或x
        String idCardRegex = "^\\d{17}[\\dXx]$";
        return matches(idCard, idCardRegex);
    }

    /**
     * 检查字符串是否为有效的 URL。
     *
     * @param url 待验证的 URL 字符串
     * @return URL 有效返回 true，否则返回 false
     */
    public static boolean isUrl(String url) {
        // URL的正则表达式，匹配常见的 URL 格式，包括http和https协议，域名和路径部分
        String urlRegex = "(https?://)?[\\w-]+(\\.[\\w-]+)+([\\w.,@?^=%&:/~+#-]*[\\w@?^=%&/~+#-])?";
        return matches(url, urlRegex);
    }

    /**
     * 检查字符串是否为有效的 IP 地址。
     * @param ip 待验证的 IP 地址字符串
     * @return IP 地址有效返回 true，否则返回 false
     */
    public static boolean isIp(String ip) {
        // IP地址的正则表达式，匹配 IPv4 地址格式，由四个0到255的数字组成，以点分隔
        String ipRegex = "^((25[0-5]|2[0-4]\\d|[01]?\\d\\d?|)\\.){3}(25[0-5]|2[0-4]\\d|[01]?\\d\\d?)$";
        return matches(ip, ipRegex);
    }

    /**
     * 检查字符串是否匹配给定的正则表达式。
     *
     * @param input 待验证的字符串
     * @param regex 正则表达式
     * @return 匹配返回 true，否则返回 false
     */
    public static boolean matches(String input, String regex) {
        if (input == null || regex == null) {
            return false;
        }
        try {
            return input.matches(regex);
        } catch (Exception e) {
            return false;
        }
    }
}