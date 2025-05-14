package hot_100.二叉树;

import java.util.HashMap;
import java.util.Map;

/**
 * 路径总和 III -- 树、深度优先搜索、二叉树
 *
 * @author chenmeng
 */
@SuppressWarnings("all")
class _437_路径总和 {

    /* 给定一个二叉树的根节点 root ，和一个整数 targetSum ，求该二叉树里节点值之和等于 targetSum 的 路径 的数目。
       路径 不需要从根节点开始，也不需要在叶子节点结束，但是路径方向必须是向下的（只能从父节点到子节点）。 */

    /**
     * 哈希表 + 前缀和 + 递归
     */
    class Solution {
        // 用于记录前缀和出现的次数
        private Map<Long, Integer> cnt = new HashMap<>();
        // 目标路径和
        private int targetSum;

        public int pathSum(TreeNode root, int targetSum) {
            // 初始化前缀和映射，0 出现了一次（空路径）
            cnt.put(0L, 1);
            this.targetSum = targetSum;
            return dfs(root, 0);
        }

        private int dfs(TreeNode node, long s) {
            if (node == null) {
                return 0;
            }

            // 当前路径和
            s += node.val;

            // 检查是否存在满足条件的路径：当前前缀和减去目标值已被记录
            int ans = cnt.getOrDefault(s - targetSum, 0);

            // 将当前前缀和加入映射表（1：要增加的计数值。Integer::sum：合并函数，用于当键已存在时将旧值与新值相加。）
            cnt.merge(s, 1, Integer::sum);

            // 向左右子节点递归探索
            ans += dfs(node.left, s);
            ans += dfs(node.right, s);

            // 回溯，将当前前缀和从映射表中移除
            cnt.merge(s, -1, Integer::sum);
            return ans;
        }
    }
}
