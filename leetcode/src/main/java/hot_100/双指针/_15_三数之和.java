package hot_100.双指针;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 三数之和 -- 数组, 双指针, 排序
 *
 * @author cmty256
 **/
@SuppressWarnings("all")
public class _15_三数之和 {

    /* 给你一个整数数组 nums ，判断是否存在三元组 [nums[i], nums[j], nums[k]]
       满足 i != j、i != k 且 j != k, 同时还满足 nums[i] + nums[j] + nums[k] == 0 。
       请你返回所有和为 0 且不重复的三元组。
       注意：答案中不可以包含重复的三元组。 */

    /**
     * 1、排序 + 双指针 -- 30ms(80.97%), 50.38MB(18.34%)
     * <p>
     * 时间复杂度为 O(n^2)
     * <p>
     * 空间复杂度为 O(logn)
     */
   class Solution {
        public List<List<Integer>> threeSum(int[] nums) {
            // 对数组进行排序
            Arrays.sort(nums);
            List<List<Integer>> res = new ArrayList<>();

            int n = nums.length;
            // 遍历数组
            for (int first = 0; first < n; first++) {
                // 如果当前元素和前一个元素相等，则跳过
                if (first > 0 && nums[first] == nums[first - 1]) {
                    continue;
                }

                int third = n - 1;
                int target = -nums[first];
                // 遍历数组
                for (int second = first + 1; second < n; second++) {
                    // 如果当前元素和前一个元素相等，则跳过
                    if (second > first + 1 && nums[second] == nums[second - 1]) {
                        continue;
                    }
                    // 如果当前元素和最后一个元素相加大于target，则将third减一
                    while (second < third && nums[second] + nums[third] > target) {
                        third--;
                    }
                    // 如果second等于third，则跳出循环
                    if (second == third) {
                        break;
                    }
                    // 如果当前元素和最后一个元素相加等于target，则将当前元素、second和third添加到list中
                    if (nums[second] + nums[third] == target) {
                        List<Integer> list = new ArrayList<>();
                        list.add(nums[first]);
                        list.add(nums[second]);
                        list.add(nums[third]);
                        res.add(list);
                    }
                }
            }
            return res;
        }
    }
}
