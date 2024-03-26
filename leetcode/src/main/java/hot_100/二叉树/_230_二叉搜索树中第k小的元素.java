package hot_100.二叉树;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Queue;
import java.util.Stack;

/**
 * 验证二叉搜索树 -- 树、深度优先搜索、二叉搜索树、二叉树
 *
 * @author cmty256
 **/
@SuppressWarnings("all")
public class _230_二叉搜索树中第k小的元素 {

    /* 给定一个二叉搜索树的根节点 root ，和一个整数 k ，
       请你设计一个算法查找其中第 k 个最小元素（从 1 开始计数）。*/

    /**
     * 1、中序遍历（左根右） -- 0ms(100.00%), 43.52MB(6.72%)
     * <p>
     * 时间复杂度：O(n)
     * <p>
     * 空间复杂度：O(n)
     */
    class Solution {

        int res, k;

        public int kthSmallest(TreeNode root, int k) {
            this.k = k;
            dfs(root);
            return res;
        }

        void dfs(TreeNode root) {
            if (root == null) {
                return;
            }
            // 左 -- 根 -- 右
            // 递归左子树
            dfs(root.left);

            // 返回当前节点的根节点值
            if (k == 0) {
                return;
            }
            // 返回当前节点值
            if (--k == 0) {
                res = root.val;
            }

            // 递归右子树
            dfs(root.right);
        }
    }

    /**
     * 2、中序遍历 -- 0ms(100.00%), 43.36MB(25.99%)
     * <p>
     * 时间复杂度：O(n)
     * <p>
     * 空间复杂度：O(n)
     */
    class Solution2 {
        public int kthSmallest(TreeNode root, int k) {
            Stack<TreeNode> stk = new Stack<>();
            while (root != null || !stk.isEmpty()) {
                if (root != null) {
                    stk.push(root);
                    root = root.left;
                } else {
                    root = stk.pop();
                    if (--k == 0) {
                        return root.val;
                    }
                    root = root.right;
                }
            }
            return 0;
        }
    }

}

