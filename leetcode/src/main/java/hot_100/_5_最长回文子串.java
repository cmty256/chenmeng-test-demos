package hot_100;


/**
 * 最长回文子串 -- 字符串, 动态规划
 *
 * @author 沉梦听雨
 **/
@SuppressWarnings("all")
public class _5_最长回文子串 {

    /**
     * 1、动态规划 -- 131ms(34.52%), 45.06MB(9.38%)
     * <p>
     * 时间复杂度为 O(n^2), 代码中使用了两层嵌套循环来遍历整个字符串
     * <p>
     * 空间复杂度为 O(n^2), 代码中创建了一个大小为 n x n 的二维布尔数组 dp 用于存储每个位置的回文信息
     */
    class Solution1 {
        public String longestPalindrome(String s) {
            // 如果字符串为空或者长度小于2，则直接返回
            if (s == null || s.length() < 2) {
                return s;
            }

            // 定义字符串长度
            int strLen = s.length();
            // 定义最大回文串的起始位置
            int maxStart = 0;
            // 定义最大回文串的结束位置
            int maxEnd = 0;
            // 定义最大回文串的长度
            int maxLen = 1;

            // 定义二维数组，用于记录字符串中每个位置的回文信息
            boolean[][] dp = new boolean[strLen][strLen];

            // 右边界, 从第二个位置开始遍历字符串
            for (int right = 1; right < strLen; right++) {
                // 左边界, 从第一个位置开始遍历字符串
                for (int left = 0; left < right; left++) {
                    // 如果字符串中两个位置的字符相同，且两个位置之间的字符串长度小于等于2或者dp[left + 1][right - 1]为true，则dp[left][right]为true
                    if (s.charAt(left) == s.charAt(right)
                            && ((right - left) <= 2 || dp[left + 1][right - 1])) {
                        // 将该子串设为回文串
                        dp[left][right] = true;

                        // 如果当前回文串的长度大于最大回文串的长度，则更新最大回文串的起始位置和结束位置
                        if ((right - left + 1) > maxLen) {
                            maxLen = right - left + 1;
                            maxStart = left;
                            maxEnd = right;
                        }
                    }
                }
            }

            // 返回最大回文串, +1 是为了返回的子串包括下标为 maxEnd 的字符
            return s.substring(maxStart, maxEnd + 1);
        }
    }

    /**
     * 2、暴力解法 -- 超出时间限制
     * <p>
     * 时间复杂度为 O(n^3), 两层嵌套循环来枚举每个字符作为回文串的中心，然后在第三层循环中判断子串是否为回文串
     * <p>
     * 空间复杂度为 O(n^1), 代码中没有额外使用与输入字符串长度相关的空间
     */
    class Solution2 {

        public String longestPalindrome(String s) {
            String ans = "";
            int max = 0;
            int len = s.length();
            // 枚举每一个字符作为回文串的中心
            for (int i = 0; i < len; i++)
                for (int j = i + 1; j <= len; j++) {
                    String test = s.substring(i, j);
                    // 判断子串是否为回文串
                    if (isPalindromic(test) && test.length() > max) {
                        ans = s.substring(i, j);
                        max = Math.max(max, ans.length());
                    }
                }
            return ans;
        }

        public boolean isPalindromic(String s) {
            int len = s.length();
            // 判断子串是否为回文串
            for (int i = 0; i < len / 2; i++) {
                if (s.charAt(i) != s.charAt(len - i - 1)) {
                    return false;
                }
            }
            return true;
        }
    }
}
