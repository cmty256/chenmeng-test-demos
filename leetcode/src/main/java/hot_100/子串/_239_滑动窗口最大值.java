package hot_100.子串;


import java.util.Deque;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

/**
 * 滑动窗口最大值 -- 队列, 数组, 滑动窗口, 单调队列, 堆（优先队列）
 *
 * @author 沉梦听雨
 **/
@SuppressWarnings("all")
public class _239_滑动窗口最大值 {

    /* 给你一个整数数组 nums，
       有一个大小为 k 的滑动窗口从数组的最左侧移动到数组的最右侧。
       你只可以看到在滑动窗口内的 k 个数字。滑动窗口每次只向右移动一位。
       返回 滑动窗口中的最大值 。 */

    /**
     * 1、单调队列（双向队列的一种） -- 29(67.93%), 58.4MB(35.91%)
     * <p>
     * 时间复杂度分析：O(n)，其中 n 是数组的长度。
     * <p>
     * 空间复杂度分析：O(k)。
     */
    class Solution {
        public int[] maxSlidingWindow(int[] nums, int k) {
            // 获取数组长度
            int n = nums.length;
            // 存储最终结果的数组
            int[] result = new int[n - k + 1];
            // 双端队列，存储数组元素的索引
            Deque<Integer> deque = new LinkedList<>();

            for (int i = 0; i < nums.length; i++) {
                // 在插入新元素前，移除队列中比新元素小的元素，确保队列中的元素是降序排列的
                while (!deque.isEmpty() && nums[i] >= nums[deque.peekLast()]) {
                    deque.pollLast();
                }

                // 将当前元素的索引加入队列
                deque.offerLast(i);

                // 移除窗口外的元素，即窗口的大小超过 k
                if (i - deque.peekFirst() >= k) {
                    deque.pollFirst();
                }

                // 计算当前窗口的最大值，存入结果数组中
                if (i + 1 >= k) {
                    result[i + 1 - k] = nums[deque.peekFirst()];
                }
            }

            // 返回结果数组
            return result;
        }
    }
}

