package hot_100.双指针;


/**
 * 盛最多水的容器 -- 贪心, 数组, 双指针
 *
 * @author chenmeng
 **/
@SuppressWarnings("all")
public class _11_盛最多水的容器 {

    /* 给定一个长度为 n 的整数数组 height 。
    有 n 条垂线，第 i 条线的两个端点是 (i, 0) 和 (i, height[i]) 。
    找出其中的两条线，使得它们与 x 轴共同构成的容器可以容纳最多的水。
    返回容器可以储存的最大水量。 */

    /**
     * 1、双指针 -- 4ms(60.90%), 54.76MB(19.09%)
     * <p>
     * 时间复杂度为 O(n)，其中n是数组height的长度。这是因为在最坏的情况下，我们需要遍历整个数组一次，每次迭代中计算面积并更新最大值的操作都是常数时间复杂度的。因此，总的时间复杂度是线性的。
     * 空间复杂度为 O(1)，因为我们只使用了固定数量的变量来存储最大面积和左右指针的位置，这些变量的空间需求不随输入数组的大小而改变。
     */
    class Solution {
        public int maxArea(int[] height) {
            // 初始化最大面积
            int max = 0;
            // 初始化左右指针
            int left = 0, right = height.length - 1;

            // 当左右指针未重合时
            while (left < right) {
                // 计算当前面积
                int area = Math.min(height[left], height[right]) * (right - left);
                // 比较当前面积和最大面积，取最大面积
                max = Math.max(max, area);

                // 移动值较小的指针
                if (height[left] < height[right]) {
                    left++;
                } else {
                    right--;
                }
            }

            // 返回最大面积
            return max;
        }
    }
}
