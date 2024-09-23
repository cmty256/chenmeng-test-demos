package hot_100.二叉树;

import java.util.ArrayDeque;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

/**
 * 翻转二叉树 -- 树、深度优先搜索、广度优先搜索、二叉树
 *
 * @author chenmeng
 **/
@SuppressWarnings("all")
public class _226_翻转二叉树 {

    /* 给你一棵二叉树的根节点 root, 翻转这棵二叉树，并返回其根节点。*/

    /**
     * 1、递归（DFS） -- 0ms(100.00%), 40.19MB(5.04%)
     * <p>
     * 时间复杂度：O(n)
     * <p>
     * 空间复杂度：O(n)
     */
    class Solution {
        public TreeNode invertTree(TreeNode root) {
            if (root == null) {
                return null;
            }

            // 递归左子树
            TreeNode left = invertTree(root.left);
            // 递归右子树
            TreeNode right = invertTree(root.right);

            // 翻转
            root.left = right;
            root.right = left;
            return root;
        }
    }

    /**
     * 2、队列（BFS） -- 0ms(100.00%), 40.00MB(17.56%)
     * <p>
     * 时间复杂度：O(n)
     * <p>
     * 空间复杂度：O(n)
     */
    class Solution2 {
        public TreeNode invertTree(TreeNode root) {
            if (root == null) {
                return null;
            }
            // Queue<TreeNode> queue = new LinkedList<>();
            Queue<TreeNode> queue = new ArrayDeque<>();
            queue.offer(root);

            while (!queue.isEmpty()) {
                TreeNode cur = queue.poll();

                TreeNode temp = cur.left;
                cur.left = cur.right;
                cur.right = temp;

                if (cur.left != null) {
                    queue.offer(cur.left);
                }
                if (cur.right != null) {
                    queue.offer(cur.right);
                }
            }

            return root;
        }
    }

    /**
     * 3、栈（DFS） -- 0ms(100.00%), 40.21MB(5.04%)
     * <p>
     * 时间复杂度：O(n)
     * <p>
     * 空间复杂度：O(n)
     */
    class Solution3 {
        public TreeNode invertTree(TreeNode root) {
            if (root == null) {
                return null;
            }
            Stack<TreeNode> stack = new Stack<>();
            stack.push(root);

            while (!stack.isEmpty()) {
                TreeNode cur = stack.pop();

                TreeNode tmp = cur.left;
                cur.left = cur.right;
                cur.right = tmp;

                if (cur.left != null) {
                    stack.push(cur.left);
                }
                if (cur.right != null) {
                    stack.push(cur.right);
                }
            }

            return root;
        }
    }

}

