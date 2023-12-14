package hot_100;

import java.util.HashMap;
import java.util.Map;

/**
 * 两数之和 -- 数组, 哈希表
 *
 * @author 沉梦听雨
 **/
@SuppressWarnings("all") // 禁止所有检查
public class _1_两数之和 {

    /* 给定一个整数数组 nums 和一个整数目标值 target，
       请你在该数组中找出 和为目标值 target  的那 两个 整数，并返回它们的数组下标。
       你可以假设每种输入只会对应一个答案。但是，数组中同一个元素在答案里不能重复出现。
       你可以按任意顺序返回答案。 */

    /**
     * 1、暴力枚举 -- 49ms(40.58%), 42.8MB(9.48%)
     */
    class Solution1 {
        public int[] twoSum(int[] nums, int target) {
            int[] result = new int[2];
            for (int i = 0; i < nums.length; i++) {
                for (int j = i + 1; j < nums.length; j++) {
                    if (nums[i] + nums[j] == target) {
                        // return new int[]{i, j};
                        result[0] = i;
                        result[1] = j;
                    }
                }
            }
            return result;
        }
    }

    /**
     * 2、哈希表映射 -- 1ms(99.52%), 42.9MB(5.7%)
     */
   class Solution2 {
        public int[] twoSum(int[] nums, int target) {
            // 创建一个HashMap
            Map<Integer, Integer> map = new HashMap<>();
            // 遍历数组
            for (int i = 0; i < nums.length; i++) {
                // 如果map中包含target - nums[i]，则返回map中target - nums[i]对应的值和i
                if (map.containsKey(target - nums[i])) {
                    return new int[]{map.get(target - nums[i]), i};
                }
                // 将nums[i]和i放入map中
                map.put(nums[i], i);
            }
            // 如果没有找到两个数的和等于target，抛出异常
            throw new IllegalArgumentException("No two sum solution");
        }
    }
}
