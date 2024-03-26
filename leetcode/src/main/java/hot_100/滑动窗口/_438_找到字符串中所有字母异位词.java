package hot_100.滑动窗口;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 找到字符串中所有字母异位词 -- 哈希表, 字符串, 滑动窗口
 *
 * @author cmty256
 **/
@SuppressWarnings("all")
public class _438_找到字符串中所有字母异位词 {

    /* 给定两个字符串 s 和 p，找到 s 中所有 p 的 异位词 的子串，
       返回这些子串的起始索引。不考虑答案输出的顺序。
       异位词 指由相同字母重排列形成的字符串（包括相同的字符串）。 */

    /**
     * 1、滑动窗口 -- 38ms(26.77%), 42.73MB(62.58%)
     * <p>
     * 时间复杂度为 O(p + s), p 是字符串 p 的长度, s是字符串 s 的长度
     * <p>
     * 空间复杂度为 O(p), p 是字符串 p 的长度
     */
    class Solution {
        public List<Integer> findAnagrams(String s, String p) {
            List<Integer> result = new ArrayList<>();

            // 存储目标字符串 p 中每个字符的出现次数
            HashMap<Character, Integer> targetFreqMap = new HashMap<>();
            for (char c : p.toCharArray()) {
                targetFreqMap.put(c, targetFreqMap.getOrDefault(c, 0) + 1);
            }

            int pLen = p.length();
            int left = 0, right = 0, count = pLen;

            while (right < s.length()) {
                char currentChar = s.charAt(right);

                // 如果当前字符是目标字符串 p 中的字符，更新 count
                if (targetFreqMap.containsKey(currentChar) && targetFreqMap.get(currentChar) > 0) {
                    count--;
                }

                // 更新目标字符串中当前字符的出现次数
                targetFreqMap.put(currentChar, targetFreqMap.getOrDefault(currentChar, 0) - 1);

                // 移动右指针
                right++;

                // 当 count 等于 0 时，表示找到一个合法的字母异位词
                if (count == 0) {
                    result.add(left);
                }

                // 当窗口大小等于目标字符串长度时，左指针右移，恢复 count 和频率表
                if (right - left == pLen) {
                    char leftChar = s.charAt(left);

                    // 恢复 count
                    if (targetFreqMap.containsKey(leftChar) && targetFreqMap.get(leftChar) >= 0) {
                        count++;
                    }

                    // 恢复频率表
                    targetFreqMap.put(leftChar, targetFreqMap.getOrDefault(leftChar, 0) + 1);

                    // 移动左指针
                    left++;
                }
            }

            return result;
        }
    }

    /**
     * 2、滑动窗口优化版（双指针+数组） -- 4ms(98.83%), 42.32MB(97.00%)
     * <p>
     * 时间复杂度为 O(m + n), m 是字符串 s 的长度, n 是字符串 p 的长度
     * <p>
     * 空间复杂度为 O(C), C 是字符集大小, 即 C = 26
     */
    class Solution2 {
        public List<Integer> findAnagrams(String s, String p) {
            // 计算s和p的长度
            int m = s.length(), n = p.length();
            // 创建一个存储结果的列表
            List<Integer> res = new ArrayList<>();
            // 如果s的长度小于p的长度，则直接返回空列表
            if (m < n) {
                return res;
            }
            // 创建一个长度为26的数组，用来存储p中每个字符出现的次数
            int[] cnt1 = new int[26];
            // 遍历p，将每个字符出现的次数存入cnt1中
            for (int i = 0; i < n; ++i) {
                cnt1[p.charAt(i) - 'a']++;
            }
            // 创建一个长度为26的数组，用来存储s中每个字符出现的次数
            int[] cnt2 = new int[26];
            // 遍历s，将每个字符出现的次数存入cnt2中
            for (int i = 0, j = 0; i < m; ++i) {
                int k = s.charAt(i) - 'a';
                cnt2[k]++;
                // 如果cnt2中某个字符出现的次数大于cnt1中对应字符出现的次数，则将cnt2中对应字符出现的次数减1，并且j自增1
                while (cnt2[k] > cnt1[k]) {
                    cnt2[s.charAt(j++) - 'a']--;
                }
                // 如果i - j + 1等于n，则将j的值存入res中
                if (i - j + 1 == n) {
                    res.add(j);
                }
            }
            // 返回res
            return res;
        }
    }
}
