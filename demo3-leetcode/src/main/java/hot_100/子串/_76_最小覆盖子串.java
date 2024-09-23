package hot_100.子串;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

/**
 * 最小覆盖子串 -- 哈希表、字符串、滑动窗口
 *
 * @author chenmeng
 **/
@SuppressWarnings("all")
public class _76_最小覆盖子串 {

    /* 给你一个字符串 s 、一个字符串 t 。
       返回 s 中涵盖 t 所有字符的最小子串。
       如果 s 中不存在涵盖 t 所有字符的子串，则返回空字符串 "" 。 */

    /**
     * 1、滑动窗口解法 -- 双指针 + 哈希表 -- 11ms(75.19%), 42.69MB(78.76%)
     * <p>
     * 时间复杂度为：O(m + 2m) = O(m)，其中 m 是源字符串的长度。
     * <p>
     * 空间复杂度为：O(C)，其中 C 为字符集大小。
     */
    class Solution {
        public String minWindow(String s, String t) {
            int m = s.length();
            int n = t.length();

            // 如果s的长度小于t的长度，则返回空字符串
            if (m < n) {
                return "";
            }

            // 创建一个哈希表来存储目标字符串 t 中每个字符的出现次数
            HashMap<Character, Integer> map = new HashMap<>();
            for (char c : t.toCharArray()) {
                map.put(c, map.getOrDefault(c, 0) + 1);
            }

            // 初始化左右指针、字符计数和最小窗口长度及起始位置
            int left = 0, right = 0, count = n;
            int minLen = Integer.MAX_VALUE;
            int minLeft = 0;

            // 遍历源字符串 s
            while (right < m) {
                char c = s.charAt(right);

                // 如果字符 c 在目标字符串 t 中出现，更新计数和哈希表
                if (map.containsKey(c)) {
                    if (map.get(c) > 0) {
                        count--;
                    }
                    map.put(c, map.get(c) - 1);
                }

                // 如果count等于0，则表示当前窗口包含 t 中的所有字符，移动左指针以找到最小窗口
                while (count == 0) {

                    // 如果当前窗口的长度小于minLen，更新最小窗口的长度和位置
                    if (right - left < minLen) {
                        minLen = right - left;
                        minLeft = left;
                    }

                    // 从左边开始收缩窗口：移动左指针，使窗口变得无效
                    char leftChar = s.charAt(left);
                    if (map.containsKey(leftChar)) {
                        map.put(leftChar, map.get(leftChar) + 1);
                        if (map.get(leftChar) > 0) {
                            count++;
                        }
                    }

                    left++;
                }

                // 移动右指针，扩大窗口
                right++;
            }

            // 如果minLen等于Integer.MAX_VALUE，则表示没有符合条件的窗口，返回空字符串
            // 否则根据最小窗口的位置和长度得到结果子串
            return minLen == Integer.MAX_VALUE ? "" : s.substring(minLeft, minLeft + minLen + 1);
        }
    }
}
