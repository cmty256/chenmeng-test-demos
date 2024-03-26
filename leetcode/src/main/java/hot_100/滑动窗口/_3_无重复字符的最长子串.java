package hot_100.滑动窗口;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

/**
 * 无重复字符的最长子串 -- 哈希表、字符串、滑动窗口
 *
 * @author cmty256
 **/
@SuppressWarnings("all")
public class _3_无重复字符的最长子串 {

    /**
     * 1、滑动窗口解法 -- 双指针 + 哈希表 -- 7ms(28.98%), 42.81MB(21.13%)
     */
    class Solution1 {
        public int lengthOfLongestSubstring(String s) {
            // 创建一个哈希表，用来存储字符和位置
            HashMap<Character, Integer> map = new HashMap<>();
            // 定义 左 右 指针
            int left = 0;
            int maxLen = 0;

            // 遍历字符串
            for (int right = 0; right < s.length(); right++) {
                // 获取当前字符
                char c = s.charAt(right);
                // 如果哈希表中已经存在当前字符，则更新左指针
                if (map.containsKey(c)) {
                    left = Math.max(left, map.get(c) + 1);
                }
                // 将当前字符和位置存入哈希表
                map.put(c, right);
                // 更新最大长度
                maxLen = Math.max(maxLen, right - left + 1);
            }

            return maxLen;
        }
    }

    /**
     * 2、滑动窗口解法 -- 双指针 + HashSet -- 9ms(20.64%), 43MB(13.47%)
     */
    class Solution2 {
        public int lengthOfLongestSubstring(String s) {
            // 哈希集合，记录每个字符是否出现过
            Set<Character> hashSet = new HashSet<Character>();
            int n = s.length();

            // 右指针，初始值为-1，相当于我们在字符串的左边界的左侧，还没有开始移动
            int rk = -1, ans = 0;
            for (int i = 0; i < n; i++) {
                // 判断是否为第一个字符
                if (i != 0) {
                    // 否，左指针向右移动一格，移除一个字符
                    hashSet.remove(s.charAt(i - 1));
                }
                // 当没有越界 且 右指针的下一个字符没有出现过时
                while (rk + 1 < n && !hashSet.contains(s.charAt(rk + 1))) {
                    // 不断地移动右指针，并将该字符添加到集合 occ 中
                    hashSet.add(s.charAt(rk + 1));
                    rk++;
                }
                // 计算当前找到的最长无重复字符子串的长度，并与之前的结果取最大值
                ans = Math.max(ans, rk + 1 - i);
            }
            return ans;
        }
    }

}
