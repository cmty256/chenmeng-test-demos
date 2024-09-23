package hot_100.二叉树;

import java.util.LinkedList;
import java.util.Queue;

/**
 * 二叉树的直径 -- 树、深度优先搜索、二叉树
 *
 * @author chenmeng
 **/
@SuppressWarnings("all")
public class _543_二叉树的直径 {

    /* 给你一棵二叉树的根节点，返回该树的 直径 。
       二叉树的 直径 是指树中任意两个节点之间最长路径的 长度 。这条路径可能经过也可能不经过根节点 root 。
       两节点之间路径的 长度 由它们之间边数表示。*/

    /**
     * 1、递归 -- 0ms(100.00%), 41.93MB(5.15%)
     * <p>
     * 时间复杂度：O(n)
     * <p>
     * 空间复杂度：O(n)
     */
    class Solution {
        // 全局变量，统计路径节点数
        int res;

        public int diameterOfBinaryTree(TreeNode root) {
            // 二叉树的直径（最长路径上的节点数）
            res = 1;
            // 计算子树的深度
            depth(root);
            // 节点数 - 1 为边数
            return res - 1;
        }

        // 定义一个方法 depth 用来求二叉树的最大深度
        public int depth(TreeNode node) {
            // 访问到空节点了，返回0
            if (node == null) {
                return 0;
            }
            // 当前节点的左子树的深度
            int L = depth(node.left);
            // 当前节点的右子树的深度
            int R = depth(node.right);

            // 更新 ans 的值，以找到最长路径上的节点数
            res = Math.max(res, L + R + 1);

            // 返回当前节点的深度。
            return Math.max(L, R) + 1;
        }
    }

}

