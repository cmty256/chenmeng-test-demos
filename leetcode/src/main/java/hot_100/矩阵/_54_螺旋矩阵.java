package hot_100.矩阵;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 螺旋矩阵 -- 数组、矩阵、模拟
 *
 * @author 沉梦听雨
 **/
@SuppressWarnings("all")
public class _54_螺旋矩阵 {

    /* 给你一个 m 行 n 列的矩阵 matrix ，
       请按照 顺时针螺旋顺序 ，返回矩阵中的所有元素。 */

    /**
     * 1、使用两个标记数组：额外空间 O(m + n) -- 0ms(100.00%), 40.5MB(5.5%)
     * <p>
     * 时间复杂度：O(m * n)，其中 m 为矩阵的行数，n 为矩阵的列数。
     * <p>
     * 空间复杂度：O(1)，使用了两个额外的数组。
     */
    class Solution {
        public List<Integer> spiralOrder(int[][] matrix) {
            // 创建结果集
            List<Integer> result = new ArrayList<>();
            // 由于题目提示 m,n 一定大于等于 1，所以这里可不写
            // if (matrix == null || matrix.length == 0 || matrix[0].length == 0) {
            //     return result;
            // }
            // 表示当前层的边界
            int top = 0, bottom = matrix.length - 1, left = 0, right = matrix[0].length - 1;

            while (top <= bottom && left <= right) {
                // 从左到右：遍历当前层的最上面一行
                for (int i = left; i <= right; i++) {
                    result.add(matrix[top][i]);
                }
                top++;

                // 从上到下：遍历当前层的最右边一列
                for (int i = top; i <= bottom; i++) {
                    result.add(matrix[i][right]);
                }
                right--;

                // 从右到左：遍历当前层的最下面一行
                if (top <= bottom) {
                    for (int i = right; i >= left; i--) {
                        result.add(matrix[bottom][i]);
                    }
                    bottom--;
                }

                // 从下到上：遍历当前层的最左边一列
                if (left <= right) {
                    for (int i = bottom; i >= top; i--) {
                        result.add(matrix[i][left]);
                    }
                    left++;
                }
            }

            return result;
        }
    }

    /**
     * 2、模拟，作者：Krahets -- 0ms(100.00%), 40.4MB(5.5%)
     * 原文链接：https://leetcode.cn/problems/spiral-matrix/solutions/2362055/54-luo-xuan-ju-zhen-mo-ni-qing-xi-tu-jie-juvi/
     * <p>
     * 时间复杂度：O(m * n)，其中 m 为矩阵的行数，n 为矩阵的列数。
     * <p>
     * 空间复杂度：O(1)，使用常量空间。
     */
    class Solution2 {
        public List<Integer> spiralOrder(int[][] matrix) {
            // 判断矩阵是否为空
            if (matrix.length == 0)
                return new ArrayList<Integer>();
            // 定义矩阵的左右上下的边界
            int l = 0, r = matrix[0].length - 1, t = 0, b = matrix.length - 1, x = 0;
            // 定义一个长度为矩阵元素个数的数组
            Integer[] res = new Integer[(r + 1) * (b + 1)];
            // 开始遍历矩阵
            while (true) {
                // 从左到右遍历上边界
                for (int i = l; i <= r; i++) res[x++] = matrix[t][i];
                // 上边界下移
                if (++t > b) break;
                // 从上到下遍历右边界
                for (int i = t; i <= b; i++) res[x++] = matrix[i][r];
                // 右边界左移
                if (l > --r) break;
                // 从右到左遍历下边界
                for (int i = r; i >= l; i--) res[x++] = matrix[b][i];
                // 下边界上移
                if (t > --b) break;
                // 从下到上遍历左边界
                for (int i = b; i >= t; i--) res[x++] = matrix[i][l];
                // 左边界右移
                if (++l > r) break;
            }
            // 返回矩阵的螺旋遍历结果
            return Arrays.asList(res);
        }
    }

}
