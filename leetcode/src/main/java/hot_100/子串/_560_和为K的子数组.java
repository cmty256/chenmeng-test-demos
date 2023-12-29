package hot_100.子串;


import java.util.HashMap;
import java.util.Map;

/**
 * 和为 K 的子数组 -- 数组, 哈希表, 前缀和
 *
 * @author 沉梦听雨
 **/
@SuppressWarnings("all")
public class _560_和为K的子数组 {

    /* 给你一个整数数组 nums 和一个整数 k ，
       请你统计并返回 该数组中和为 k 的子数组的个数 。
       子数组是数组中元素的连续非空序列。 */

    /**
     * 1、暴力解法 -- 1606ms(31.36%), 44.44MB(53.38%)
     * <p>
     * 时间复杂度分析：O(n^2)，其中 n 是数组的长度。
     * <p>
     * 空间复杂度分析：O(1)。
     */
    public class Solution {
        public int subarraySum(int[] nums, int k) {
            int count = 0;
            int len = nums.length;

            for (int left = 0; left < len; left++) {
                int sum = 0;
                for (int right = left; right < len; right++) {
                    sum += nums[right];
                    if (sum == k) {
                        count++;
                    }
                }
            }

            return count;
        }
    }

    /**
     * 2、前缀和 + 哈希表 -- 25ms(54.20%), 46.03MB(5.03%)
     * <p>
     * 时间复杂度：O(n)。
     * <p>
     * 空间复杂度为：O(n)。
     */
    class Solution2 {
        public int subarraySum(int[] nums, int k) {
            // 创建一个哈希表，用于存储前缀和及其出现的次数
            Map<Integer, Integer> counter = new HashMap<>();
            // 初始化哈希表，表示前缀和为 0 出现了 1 次
            counter.put(0, 1);
            // 初始化答案为 0，s 用于表示当前的前缀和
            int ans = 0, s = 0;

            // 遍历数组 nums 中的每个元素
            for (int num : nums) {
                // 计算当前的前缀和
                s += num;

                // 更新答案，如果存在前缀和为 (s - k) 的情况，累加对应次数到答案中
                ans += counter.getOrDefault(s - k, 0);

                // 更新哈希表，记录当前前缀和出现的次数
                counter.put(s, counter.getOrDefault(s, 0) + 1);
            }

            // 返回最终答案
            return ans;
        }
    }

    // public static void main(String[] args) {
    //     int[] nums = {1, 1, 1};
    //     int k = 2;
    //     System.out.println(new Solution2().subarraySum(nums, k));
    // }
}

