package hot_100;


/**
 * 接雨水 -- 栈, 数组, 双指针, 动态规划, 单调栈
 *
 * @author 沉梦听雨
 **/
@SuppressWarnings("all")
public class _42_接雨水 {

    /* 给定 n 个非负整数表示每个宽度为 1 的柱子的高度图，
       计算按此排列的柱子，下雨之后能接多少雨水。
       */

    /**
     * 1、双指针 -- 0ms(100.00%), 43.96MB(5.09%)
     * <p>
     * 时间复杂度为 O(n)
     * <p>
     * 空间复杂度为 O(1)
     */
    class Solution {
        public int trap(int[] height) {
            int res = 0;
            // 定义左指针和右指针
            int left = 0, right = height.length - 1;
            // 初始化左右两边的最大高度
            int leftMax = 0, rightMax = 0;

            // 分别记录左右两边的最大高度
            while (left < right) {
                // 记录当前左边的最大高度
                leftMax = Math.max(leftMax, height[left]);
                // 记录当前右边的最大高度
                rightMax = Math.max(rightMax, height[right]);
                // 比较左右两边的最大高度，并记录
                if (height[left] < height[right]) {
                    // 此时, 左边的最大高度限制了当前位置的容纳能力。
                    // 如果当前左边的高度较小, 就用左边的最大高度 减去 当前的左边高度
                    res += leftMax - height[left];
                    left++;
                } else {
                    // 此时, 右边的最大高度限制了当前位置的容纳能力。
                    // 如果当前左边的高度 大于或等于 右边的高度, 就用右边的最大高度 减去 当前的右边高度
                    res += rightMax - height[right];
                    right--;
                }
            }

            return res;
        }
    }

    /**
     * 2、动态规划 -- 1ms(75.87%), 42.92MB(80.95%)
     * <p>
     * 时间复杂度为 O(n)
     * <p>
     * 空间复杂度为 O(n)
     */
    class Solution2 {
        public int trap(int[] height) {
            // 获取数组长度
            int n = height.length;
            // 创建两个数组，分别用于存储左侧最大值和右侧最大值
            int[] left = new int[n];
            int[] right = new int[n];
            // 初始化边界值
            left[0] = height[0];
            right[n - 1] = height[n - 1];

            // 遍历数组，计算左侧最大值和右侧最大值
            for (int i = 1; i < n; i++) {
                // 从左到右
                left[i] = Math.max(left[i - 1], height[i]);
                // 从右到左
                right[n - 1 - i] = Math.max(right[n - i], height[n - 1 - i]);
            }

            // 初始化结果变量
            int res = 0;
            // 遍历数组，计算雨水量
            for (int i = 0; i < n; ++i) {
                res += Math.min(left[i], right[i]) - height[i];
            }
            // 返回雨水量
            return res;
        }
    }

    // public static void main(String[] args) {
    //     int[] height = {0, 1, 0, 2, 1, 0, 1, 3, 2, 1, 2, 1};
    //     System.out.println(new Solution2().trap(height));
    // }
}
