package hot_100.普通数组;

import java.util.Arrays;
import java.util.HashSet;

/**
 * 缺失的第一个正数 -- 数组、哈希表
 *
 * @author 沉梦听雨
 **/
@SuppressWarnings("all")
public class _41_缺失的第一个正数 {

    /* 给你一个整数数组 nums，返回 数组 answer ，
       其中 answer[i] 等于 nums 中除 nums[i] 之外其余各元素的乘积 。
       题目数据 保证 数组 nums 之中任意元素的全部前缀元素和后缀的乘积都在  32 位 整数范围内。
       请 不要使用除法，且在 O(n) 时间复杂度内完成此题。 */

    /**
     * 1、排序 + HashSet -- 20ms(5.5%), 57MB(87.37%)
     * <p>
     * 时间复杂度为：O(nlogn)。
     * <p>
     * 额外空间复杂度为：O(1)。
     */
    class Solution {
        public int firstMissingPositive(int[] nums) {
            // 创建一个HashSet
            HashSet<Integer> set = new HashSet<>();
            // 对数组进行排序
            Arrays.sort(nums);
            // 获取数组的长度
            int n = nums.length;

            // 将数组中的元素添加到HashSet中
            for (int i = 0; i < n; i++) {
                set.add(nums[i]);
            }
            // 遍历数组，查找第一个不存在的正数
            for (int i = 1; i < nums[n - 1]; i++) {
                // 如果HashSet中不存在该正数，则返回该正数
                if (!set.contains(i)) {
                    return i;
                }
            }

            // 如果数组中的最后一个元素大于0，则返回 该元素加1，否则返回 1
            return nums[n - 1] > 0 ? (nums[n - 1] + 1) : 1;
        }
    }

    /**
     * 2、原地转换 -- 1ms(5.5%), 53.59MB(98.38%)
     * <p>
     * 时间复杂度为：O(n)。
     * <p>
     * 额外空间复杂度为：O(1)。
     */
    class Solution2 {
        public int firstMissingPositive(int[] nums) {
            int n = nums.length;

            // 遍历数组
            for (int i = 0; i < n; ++i) {
                // 将当前元素放到正确的位置上，即 nums[i] 应该等于 i + 1
                // 当前元素应该在的位置是 nums[i] - 1，如果不在则进行交换
                // (while)判断条件：当前元素是正整数，当前元素不超过数组的长度，当前元素应该在的位置的元素不等于当前元素
                while (nums[i] >= 1 && nums[i] <= n && nums[i] != nums[nums[i] - 1]) {
                    swap(nums, i, nums[i] - 1);
                }
            }

            // 再次遍历数组，寻找第一个不在正确位置上的元素（缺失的最小正整数一定在数组长度内或者数组长度+1）
            for (int i = 0; i < n; ++i) {
                if (i + 1 != nums[i]) {
                    // 如果找到第一个不在正确位置上的元素，则返回缺失的最小正整数
                    return i + 1;
                }
            }

            // 如果数组中所有元素都在正确位置上，返回数组长度加1
            return n + 1;
        }

        // 交换数组中两个元素的位置
        private void swap(int[] nums, int i, int j) {
            int t = nums[i];
            nums[i] = nums[j];
            nums[j] = t;
        }
    }
}
