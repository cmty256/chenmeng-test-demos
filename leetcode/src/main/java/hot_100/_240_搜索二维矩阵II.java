package hot_100;

import java.util.Arrays;

/**
 * 搜索二维矩阵 II -- 数组、二分查找、分治、矩阵
 *
 * @author 沉梦听雨
 **/
@SuppressWarnings("all")
public class _240_搜索二维矩阵II {

    /* 编写一个高效的算法来搜索 m x n 矩阵 matrix 中的一个目标值 target 。该矩阵具有以下特性：
       每行的元素从左到右升序排列。
       每列的元素从上到下升序排列。 */

    /**
     * 1、二分查找 -- 6ms(47.09%), 45.01MB(96.77%)
     * <p>
     * 时间复杂度：O(mlogn), 其中 m 和 n 分别为矩阵的行数和列数。
     * <p>
     * 空间复杂度：O(1)
     */
    class Solution {
        public boolean searchMatrix(int[][] matrix, int target) {
            // 遍历矩阵中的每一行，使用二分查找法查找target是否存在
            for (int[] row : matrix) {
                // 返回的 b 代表在数组 row 中查找元素 target 的位置, 即索引值
                int b = Arrays.binarySearch(row, target);
                if (b >= 0) {
                    return true;
                }
            }
            return false;
        }
    }

    /**
     * 2、贪心：从左下角或右上角搜索 -- 6ms(47.09%), 45.06MB(96.30%)
     * <p>
     * 时间复杂度：O(m + n), 其中 m 和 n 分别为矩阵的行数和列数。
     * <p>
     * 空间复杂度：O(1)
     */
    class Solution2 {
        public boolean searchMatrix(int[][] matrix, int target) {
            // 获取矩阵的行数和列数
            int m = matrix.length, n = matrix[0].length;
            // 初始化行索引和列索引（左下角）
            int i = m - 1, j = 0;
            // 当行索引大于等于0 且 列索引小于列数时，进行循环
            while (i >= 0 && j < n) {
                // 如果当前元素等于目标值，返回true
                if (matrix[i][j] == target) {
                    return true;
                }
                // 如果当前元素大于目标值，将行索引减1
                if (matrix[i][j] > target) {
                    --i;
                } else {
                    // 如果当前元素小于目标值，将列索引加1
                    ++j;
                }
            }
            // 如果循环结束后仍未找到目标值，返回false
            return false;
        }
    }

}
