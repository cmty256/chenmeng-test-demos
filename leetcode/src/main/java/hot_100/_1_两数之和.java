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
            Map<Integer, Integer> map = new HashMap<>();
            for (int i = 0; i < nums.length; i++) {
                if (map.containsKey(target - nums[i])) {
                    return new int[]{map.get(target - nums[i]), i};
                }
                map.put(nums[i], i);
            }
            throw new IllegalArgumentException("No two sum solution");
        }
    }
}
