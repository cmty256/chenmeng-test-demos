package hot_100.普通数组;

/**
 * 除自身以外数组的乘积 -- 数组、前缀和
 *
 * @author 沉梦听雨
 **/
@SuppressWarnings("all")
public class _238_除自身以外数组的乘积 {

    /* 给你一个整数数组 nums，返回 数组 answer ，
       其中 answer[i] 等于 nums 中除 nums[i] 之外其余各元素的乘积 。
       题目数据 保证 数组 nums 之中任意元素的全部前缀元素和后缀的乘积都在  32 位 整数范围内。
       请 不要使用除法，且在 O(n) 时间复杂度内完成此题。 */

    /**
     * 1、两次循环 -- 1ms(100.00%), 51.84MB(9.51%)
     * <p>
     * 时间复杂度为：O(n)。
     * <p>
     * 空间复杂度为：O(1), 忽略答案数组的空间消耗，额外空间复杂度为 O(1)。
     */
    class Solution {
        public int[] productExceptSelf(int[] nums) {
            int n = nums.length;
            int[] ans = new int[n];

            // 计算每个元素的前缀积（即下三角）
            for (int i = 0, down = 1; i < n; i++) {
                ans[i] = down;
                down *= nums[i];
            }
            // 计算每个元素的后缀积（即上三角）
            for (int i = n - 1, up = 1; i >= 0; i--) {
                ans[i] *= up;
                up *= nums[i];
            }
            return ans;
        }
    }
}
