package hot_100.哈希;


import java.util.*;

/**
 * 最长连续序列 -- 并查集, 数组, 哈希表
 *
 * @author chenmeng
 **/
@SuppressWarnings("all")
public class _128_最长连续序列 {

    /* 给定一个未排序的整数数组 nums，
       找出数字连续的最长序列（不要求序列元素在原数组中连续）的长度。
       请你设计并实现时间复杂度为 O(n) 的算法解决此问题。 */

    /**
     * 1、排序 -- 12ms(95.50%), 55.59MB(61.59%)
     * <p>
     * 时间复杂度为 O(n * logn)
     * 空间复杂度为 O(logn)
     */
    class Solution {
        public int longestConsecutive(int[] nums) {
            int len = nums.length;
            if (len < 2) {
                return len;
            }

            // 对数组进行排序
            Arrays.sort(nums);
            // 最长序列
            int res = 0;
            // 当前连续序列长度
            int sum = 0;

            // 遍历数组
            for (int i = 0; i < len; i++) {

                // 如果当前元素和下一个元素差1，则sum+1，更新res
                if ((i + 1) < len && nums[i + 1] == (nums[i] + 1)) {
                    sum += 1;
                    res = Math.max(res, sum);
                    // 如果当前元素和下一个元素相等，则跳过
                } else if ((i + 1) < len && nums[i + 1] == (nums[i])) {
                    continue;
                    // 如果当前元素和下一个元素不相等，则sum+1，更新res
                } else {
                    sum += 1;
                    res = Math.max(res, sum);
                    // 重置sum
                    sum = 0;
                }
            }

            return res;
        }
    }

    /**
     * 2、哈希表官方题解 -- -- 25ms(77.71%), 60.17MB(27.24%)
     * <p>
     * 时间复杂度为 O(n)
     * 空间复杂度为 O(n)
     */
   class Solution2 {
        public int longestConsecutive(int[] nums) {
            // 创建一个HashSet，用于存储数组中的元素
            Set<Integer> num_set = new HashSet<Integer>();
            // 遍历数组，将数组中的元素添加到HashSet中
            for (int num : nums) {
                num_set.add(num);
            }

            // 定义一个变量，用于存储最长连续序列的长度
            int longestStreak = 0;

            // 遍历HashSet，查找连续序列的最大长度
            for (int num : num_set) {
                // 如果HashSet中不包含num-1，则表示当前元素是连续序列的第一个元素
                if (!num_set.contains(num - 1)) {
                    // 定义一个变量，用于存储当前连续序列的长度
                    int currentNum = num;
                    int currentStreak = 1;

                    // 遍历当前元素，查找连续序列的最大长度
                    while (num_set.contains(currentNum + 1)) {
                        currentNum += 1;
                        currentStreak += 1;
                    }

                    // 将当前连续序列的长度与最长连续序列的长度进行比较，取最大值
                    longestStreak = Math.max(longestStreak, currentStreak);
                }
            }

            // 返回最长连续序列的长度
            return longestStreak;
        }
    }
}
