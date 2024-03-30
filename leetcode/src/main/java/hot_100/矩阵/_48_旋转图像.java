package hot_100.矩阵;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 旋转图像 -- 数组、数学、矩阵
 *
 * @author chenmeng
 **/
@SuppressWarnings("all")
public class _48_旋转图像 {

    /* 给定一个 n × n 的二维矩阵 matrix 表示一个图像。请你将图像顺时针旋转 90 度。
       你必须在 原地 旋转图像，这意味着你需要直接修改输入的二维矩阵。请不要 使用另一个矩阵来旋转图像。 */

    /**
     * 1、使用临时矩阵 -- 0ms(100.00%), 41.03MB(5.5%)
     * <p>
     * 时间复杂度：O(n ^ 2)
     * <p>
     * 空间复杂度：O(n ^ 2)
     */
    class Solution {
        public void rotate(int[][] matrix) {
            int n = matrix.length;
            // 创建一个临时矩阵
            int[][] temp = new int[n][];
            // 原矩阵深拷贝
            for (int i = 0; i < n; i++) {
                // 将原矩阵的值赋值给临时矩阵
                temp[i] = matrix[i].clone();
            }
            // 将临时矩阵的值赋值给原矩阵
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    matrix[j][n - 1 - i] = temp[i][j];
                }
            }
        }
    }

    /**
     * 2、原地翻转 -- 0ms(100.00%), 41.02MB(5.5%)
     * <p>
     * 时间复杂度：O(n ^ 2)
     * <p>
     * 空间复杂度：O(1)
     */
    class Solution2 {
        public void rotate(int[][] matrix) {
            int n = matrix.length;
            // 1、先对矩阵上下翻转（n >> 1 相当于 n / 2）
            for (int i = 0; i < n >> 1; i++) {
                for (int j = 0; j < n; j++) {
                    int temp = matrix[i][j];
                    matrix[i][j] = matrix[n - 1 - i][j];
                    matrix[n - 1 - i][j] = temp;
                }
            }
            // 2、然后对角线翻转
            for (int i = 0; i < n; i++) {
                // 注意：对角线翻转, j < i
                for (int j = 0; j < i; j++) {
                    int temp = matrix[i][j];
                    matrix[i][j] = matrix[j][i];
                    matrix[j][i] = temp;
                }
            }
        }
    }

}
