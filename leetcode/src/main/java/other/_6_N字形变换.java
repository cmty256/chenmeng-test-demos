package other;


import java.util.ArrayList;
import java.util.List;

/**
 * N字形变换 -- 字符串
 *
 * @author chenmeng
 **/
@SuppressWarnings("all")
public class _6_N字形变换 {

    /**
     * 1、模拟 Z 字形排列的过程, 巧设 flag -- 5ms(78.29%), 43.48MB(22.61%)
     * <p>
     * 时间复杂度为 O(n)，空间复杂度为 O(n)
     */
   class Solution {

        public String convert(String s, int numRows) {
            // 如果行数小于2，则直接返回
            if (numRows < 2) {
                return s;
            }

            // 创建一个StringBuilder类型的List，用于存放每一行的字符串
            List<StringBuilder> rows = new ArrayList<>();

            // 初始化每一行的StringBuilder
            for (int i = 0; i < numRows; i++) {
                rows.add(new StringBuilder());
            }

            // 初始化i和flag，用于控制行数
            int i = 0, flag = -1;
            // 遍历字符串s中的每一个字符
            for (char c : s.toCharArray()) {
                // 将字符添加到当前行中
                rows.get(i).append(c);
                // 如果当前行为第一行或者最后一行，则改变flag的值
                if (i == 0 || i == numRows - 1) {
                    flag = -flag;
                }
                // 改变行数
                i += flag;
            }
            // 创建一个StringBuilder类型的变量res，用于存放最终结果
            StringBuilder res = new StringBuilder();
            // 遍历rows中的每一个StringBuilder，将每一个StringBuilder中的字符串拼接到res中
            for (StringBuilder row : rows) {
                res.append(row);
            }
            // 返回拼接后的字符串
            return res.toString();
        }
    }

}
