package hot_100.哈希;


import java.util.*;

/**
 * 字母异位词分组 -- 数组, 哈希表, 字符串, 排序
 *
 * @author chenmeng
 **/
@SuppressWarnings("all")
public class _49_字母异位词分组 {

    /* 给你一个字符串数组，请你将 字母异位词 组合在一起。
       可以按任意顺序返回结果列表。
       字母异位词 是由重新排列源单词的所有字母得到的一个新单词。 */

    /**
     * 1、哈希表 -- 5ms(99.2%), 45.4MB(84.62%)
     * <p>
     * 时间复杂度：O(n * m * logm)
     * <p>
     * 空间复杂度：O(n * m)
     */
    class Solution {
        public List<List<String>> groupAnagrams(String[] strs) {
            Map<String, List<String>> map = new HashMap<>();

            for (String s : strs) {
                char[] t = s.toCharArray();
                Arrays.sort(t);
                String k = String.valueOf(t);

                // 1. 普通写法
                List<String> list = map.get(k);
                if (list == null) {
                    list = new ArrayList<>();
                    map.put(k, list);
                }
                list.add(s);

                // 2. Lambda表达式写法
                // map.computeIfAbsent(k, key -> new ArrayList<>())
                //         .add(s);
                //
                // 3. 利用 map 内部方法：getOrDefault
                // List<String> list = map.getOrDefault(k, new ArrayList<String>());
                // list.add(s);
                // map.put(k, list);
            }

            return new ArrayList<>(map.values());
        }
    }
}
