package hot_100.二叉树;

import java.util.ArrayDeque;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

/**
 * 对称二叉树 -- 树、深度优先搜索、广度优先搜索、二叉树
 *
 * @author 沉梦听雨
 **/
@SuppressWarnings("all")
public class _101_对称二叉树 {

    /* 给你一个二叉树的根节点 root, 检查它是否轴对称。*/

    /**
     * 1、迭代（队列） -- 1ms(19.18%), 40.98MB(5.02%)
     * <p>
     * 时间复杂度：O(n)
     * <p>
     * 空间复杂度：O(n)
     */
    class Solution {
        public boolean isSymmetric(TreeNode root) {
            if(root == null) {
                return true;
            }
            Queue<TreeNode> queue1 = new LinkedList<>();
            Queue<TreeNode> queue2 = new LinkedList<>();
            queue1.offer(root.left);
            queue2.offer(root.right);

            while (!queue1.isEmpty() && !queue2.isEmpty()) {
                TreeNode left = queue1.poll();
                TreeNode right = queue2.poll();

                if(left == null && right == null){
                    continue;
                }
                if(left == null || right == null || left.val != right.val){
                    return false;
                }

                queue1.offer(left.left);
                queue1.offer(left.right);
                queue2.offer(right.right);
                queue2.offer(right.left);
            }
            return queue1.isEmpty() && queue2.isEmpty();
        }
    }

    /**
     * 2、递归 -- 0ms(100.00%), 40.89MB(5.02%)
     * <p>
     * 时间复杂度：O(n), 因为要遍历 n 个节点
     * <p>
     * 空间复杂度：O(n), 因为递归需要栈空间, 空间复杂度是递归的深度, 也就是跟树高度有关
     */
    class Solution2 {
        public boolean isSymmetric(TreeNode root) {
            return dfs(root, root);
        }

        private boolean dfs(TreeNode root1, TreeNode root2) {
            if (root1 == null && root2 == null) {
                return true;
            }
            if (root1 == null || root2 == null || root1.val != root2.val) {
                return false;
            }
            return dfs(root1.left, root2.right) && dfs(root1.right, root2.left);
        }
    }

}

