package hot_100;

/**
 * 矩阵置零 -- 数组、哈希表、矩阵
 *
 * @author 沉梦听雨
 **/
@SuppressWarnings("all")
public class _73_矩阵置零 {

    /* 给定一个 m x n 的矩阵，如果一个元素为 0 ，
       则将其所在行和列的所有元素都设为 0 。
       请使用 原地 算法。 */

    /**
     * 1、使用两个标记数组：额外空间 O(m + n) -- 0ms(100.00%), 44.43MB(5.12%)
     * <p>
     * <p>
     * 空间复杂度：O(m + n)，使用了两个额外的数组。
     */
    class Solution {
        public void setZeroes(int[][] matrix) {
            int m = matrix.length;
            int n = matrix[0].length;
            boolean[] rows = new boolean[m];
            boolean[] cols = new boolean[n];

            // 遍历矩阵，记录包含 0 的行和列
            for (int i = 0; i < m; i++) {
                for (int j = 0; j < n; j++) {
                    if (matrix[i][j] == 0) {
                        rows[i] = true;
                        cols[j] = true;
                    }
                }
            }

            // 再次遍历矩阵，根据记录的信息将元素置为 0
            for (int i = 0; i < m; i++) {
                for (int j = 0; j < n; j++) {
                    if (rows[i] || cols[j]) {
                        matrix[i][j] = 0;
                    }
                }
            }
        }
    }

    /**
     * 2、使用常量空间的原地算法 -- 0ms(100.00%), 44.62MB(5.12%)
     * <p>
     * 时间复杂度：O(m * n)，其中 m 为矩阵的行数，n 为矩阵的列数。
     * <p>
     * 空间复杂度：O(1)，使用常量空间。
     */
    class Solution2 {
        public void setZeroes(int[][] matrix) {
            int m = matrix.length;
            int n = matrix[0].length;
            boolean firstRowHasZero = false;
            boolean firstColHasZero = false;

            // 用首行和首列标记是否包含 0
            for (int i = 0; i < m; i++) {
                for (int j = 0; j < n; j++) {
                    if (matrix[i][j] == 0) {
                        // 如果当前元素是 0，将首行和首列对应的元素置为 0
                        if (i == 0) firstRowHasZero = true;
                        if (j == 0) firstColHasZero = true;
                        matrix[i][0] = matrix[0][j] = 0;
                    }
                }
            }

            // 根据首行和首列的标记，将元素置为 0
            for (int i = 1; i < m; i++) {
                for (int j = 1; j < n; j++) {
                    if (matrix[i][0] == 0 || matrix[0][j] == 0) {
                        matrix[i][j] = 0;
                    }
                }
            }

            // 处理首行和首列
            if (firstRowHasZero) {
                for (int j = 0; j < n; j++) {
                    matrix[0][j] = 0;
                }
            }

            if (firstColHasZero) {
                for (int i = 0; i < m; i++) {
                    matrix[i][0] = 0;
                }
            }
        }
    }

}
