package hot_100;

import java.util.HashMap;

/**
 * 最大子数组和 -- 数组、分治、动态规划
 *
 * @author 沉梦听雨
 **/
@SuppressWarnings("all")
public class _53_最大子数组和 {

    /* 给你一个整数数组 nums ，
       请你找出一个具有最大和的连续子数组（子数组最少包含一个元素），返回其最大和。
       子数组 是数组中的一个连续部分。 */

    /**
     * 1、动态规划 -- 1ms(100.00%), 58.52MB(10.57%)
     * <p>
     * 时间复杂度为：O(n)。
     * <p>
     * 空间复杂度为：O(1)。
     */
    class Solution {
        public int maxSubArray(int[] nums) {
            int n = nums.length;

            // 初始化 最大子数组和
            int maxSum = nums[0];
            // 初始化 当前子数组和
            int currentSum = nums[0];

            // 从第二个元素开始遍历
            for (int i = 1; i < n; i++) {
                // 更新子数组和，当前子数组和等于 当前元素 和 当前子数组和 中的最大值
                currentSum = Math.max(nums[i], currentSum + nums[i]);
                // 更新最大子数组和
                maxSum = Math.max(maxSum, currentSum);
            }

            return maxSum;
        }
    }

    /**
     * 2、分治法 -- 12ms(5.40%), 58.3MB(15.58%)
     * <p>
     * 时间复杂度为：O(nlogn)。
     * <p>
     * 空间复杂度为：O(logn)。
     */
    class Solution2 {
        public int maxSubArray(int[] nums) {
            // if (nums == null || nums.length == 0) {
            //     return 0;
            // }

            return divideAndConquer(nums, 0, nums.length - 1);
        }

        private int divideAndConquer(int[] nums, int left, int right) {
            if (left == right) {
                return nums[left];
            }

            int mid = (left + right) / 2;

            // 分别求解左右两半的最大子数组和
            int leftMax = divideAndConquer(nums, left, mid);
            int rightMax = divideAndConquer(nums, mid + 1, right);

            // 求解跨越中间的最大子数组和
            int crossMax = maxCrossingSubarray(nums, left, mid, right);

            // 返回左半部分、右半部分和跨越中间的最大子数组和中的最大值
            return Math.max(Math.max(leftMax, rightMax), crossMax);
        }

        private int maxCrossingSubarray(int[] nums, int left, int mid, int right) {
            int leftMax = Integer.MIN_VALUE;
            int sum = 0;

            // 从中间元素向左计算最大子数组和
            for (int i = mid; i >= left; i--) {
                sum += nums[i];
                leftMax = Math.max(leftMax, sum);
            }

            int rightMax = Integer.MIN_VALUE;
            sum = 0;

            // 从中间元素向右计算最大子数组和
            for (int i = mid + 1; i <= right; i++) {
                sum += nums[i];
                rightMax = Math.max(rightMax, sum);
            }

            // 返回跨越中间的最大子数组和
            return leftMax + rightMax;
        }
    }
}
