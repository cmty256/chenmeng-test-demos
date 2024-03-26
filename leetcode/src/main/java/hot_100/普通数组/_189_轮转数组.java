package hot_100.普通数组;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 轮转数组 -- 数组、数学、双指针
 *
 * @author cmty256
 **/
@SuppressWarnings("all")
public class _189_轮转数组 {

    /* 给定一个整数数组 nums，将数组中的元素向右轮转 k 个位置，其中 k 是非负数。 */

    /**
     * 1、复制数组 -- 1ms(63.85%), 55.85MB(5.03%)
     * <p>
     * 时间复杂度为：O(n)。
     * <p>
     * 空间复杂度为：O(n)。
     */
    class Solution {
        public void rotate(int[] nums, int k) {
            int n = nums.length;
            int[] newArr = new int[n];
            for (int i = 0; i < n; i++) {
                // if (i < k) {
                //     newArr[i] = nums[n - k + i];
                // } else {
                //     newArr[i] = nums[i - k];
                // }
                newArr[(i + k) % n] = nums[i];
            }
            System.arraycopy(newArr, 0, nums, 0, n);
        }
    }

    /**
     * 2、三次翻转 -- 0ms(100.00%), 56.47MB(5.03%)
     * <p>
     * 时间复杂度为：O(n)。
     * <p>
     * 空间复杂度为：O(1)。
     */
    class Solution2 {
        public void rotate(int[] nums, int k) {
            int n = nums.length;
            // 处理 k 大于数组长度的情况
            k = k % n;

            // 先翻转整个数组
            reverse(nums, 0, n - 1);
            // 再翻转前 k 个元素
            reverse(nums, 0, k - 1);
            // 最后翻转剩余的元素
            reverse(nums, k, n - 1);
        }

        private void reverse(int[] nums, int start, int end) {
            while (start < end) {
                int temp = nums[start];
                nums[start] = nums[end];
                nums[end] = temp;
                start++;
                end--;
            }
        }
    }
}
