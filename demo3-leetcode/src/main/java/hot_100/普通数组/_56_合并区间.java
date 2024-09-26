package hot_100.普通数组;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 合并区间 -- 数组、排序
 *
 * @author chenmeng
 **/
@SuppressWarnings("all")
public class _56_合并区间 {

    /* 以数组 intervals 表示若干个区间的集合，
       其中单个区间为 intervals[i] = [starti, endi] 。
       请你合并所有重叠的区间，并返回 一个不重叠的区间数组，该数组需恰好覆盖输入中的所有区间 。 */

    /**
     * 1、排序 + 一次遍历 -- 7ms(80.92%), 44.50MB(40.15%)
     * <p>
     * 时间复杂度为：O(nlogn)。
     * <p>
     * 空间复杂度为：O(logn)。
     */
    class Solution {
        public int[][] merge(int[][] intervals) {
            // 1、根据起始值对区间进行排序（快速排序）
            Arrays.sort(intervals, (a, b) -> a[0] - b[0]);

            // 2、初始化列表以存储合并后的区间
            List<int[]> res = new ArrayList<>();

            // 3、将第一个区间添加到结果列表中
            // 假设为 [a, b]
            res.add(intervals[0]);

            // 4、遍历排序后的区间
            for (int i = 1; i < intervals.length; i++) {
                // 假设为 [c, d]
                int start = intervals[i][0], end = intervals[i][1];

                // 4.1、检查当前区间是否与结果列表中的最后一个区间重叠
                if (res.get(res.size() - 1)[1] < start) {
                    // 此时 b < c
                    // 4.1.1、如果不重叠，则将当前区间添加到结果列表中
                    res.add(intervals[i]);
                } else {
                    // 此时 b >= c, (b > d 或者 b = d 或者 b < d), 所以要以最大值更新
                    // 4.1.2、如果重叠，则通过更新最后一个区间的结束值来合并区间
                    res.get(res.size() - 1)[1] = Math.max(res.get(res.size() - 1)[1], end);
                }
            }

            // 5、将列表转换为二维数组并返回
            return res.toArray(new int[res.size()][]);
        }
    }
}
