package hot_100;


import java.util.ArrayList;
import java.util.List;

/**
 * 正则表达式匹配 -- 递归, 字符串, 动态规划
 *
 * @author chenmeng
 **/
@SuppressWarnings("all")
public class _10_正则表达式匹配 {

    /* 给你一个字符串 s 和一个字符规律 p，请你来实现一个支持 '.' 和 '*' 的正则表达式匹配。
        '.' 匹配任意单个字符
        '*' 匹配零个或多个前面的那一个元素
       所谓匹配，是要涵盖 整个 字符串 s 的，而不是部分字符串。 */

    /**
     * 1、动态规划 -- 1ms(100%), 39.79MB(85.76%)
     * <p>
     * 时间复杂度为 O(m * n)，空间复杂度为 O(m * n)
     */
    class Solution {
        public boolean isMatch(String s, String p) {
            int m = s.length();
            int n = p.length();

            // 初始化dp数组，dp[i][j]表示s的前i个字符是否与p的前j个字符匹配
            boolean[][] dp = new boolean[m + 1][n + 1];
            // 初始化 dp[o][o]= true 表示空字符串和空正则表达式是匹配的。
            dp[0][0] = true;

            // 初始化第一行，如果p的偶数位置是*，则dp[0][j] = dp[0][j - 2]
            for (int j = 2; j <= n; j += 2) {
                dp[0][j] = dp[0][j - 2] && p.charAt(j - 1) == '*';
            }

            // 遍历s和p，动态规划
            for (int i = 1; i <= m; i++) {
                for (int j = 1; j <= n; j++) {
                    // 如果p的当前字符是*，则dp[i][j] = dp[i][j - 2] || (dp[i - 1][j] && (s[i - 1] == p[j - 2] || p[j - 2] == '.'))
                    if (p.charAt(j - 1) == '*') {
                        // 考虑了 * 匹配 0 次和匹配至少 1 次的情况
                        dp[i][j] = dp[i][j - 2] || (dp[i - 1][j] && (s.charAt(i - 1) == p.charAt(j - 2) || p.charAt(j - 2) == '.'));
                    } else {
                        // 否则，dp[i][j] = dp[i - 1][j - 1] && (s[i - 1] == p[j - 1] || p[j - 1] == '.')
                        // 处理普通字符匹配的情况，确保了当前位置的匹配状态
                        dp[i][j] = dp[i - 1][j - 1] && (s.charAt(i - 1) == p.charAt(j - 1) || p.charAt(j - 1) == '.');
                    }
                }
            }

            // 返回dp[m][n]
            return dp[m][n];
        }
    }
}
