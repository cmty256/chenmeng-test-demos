package hot_100.二叉树;

import java.util.*;

/**
 * 二叉树的最大深度 -- 树、深度优先搜索、广度优先搜索、二叉树
 *
 * @author 沉梦听雨
 **/
@SuppressWarnings("all")
public class _104_二叉树的最大深度 {

    /* 给定一个二叉树的根节点 root ，返回 它的 中序 遍历（左中右） 。*/

    /**
     * 1、队列(广度优先搜索) -- 1ms(22.26%), 41.79MB(5.38%)
     * <p>
     * 时间复杂度：O(n)
     * <p>
     * 空间复杂度：O(n)
     */
    class Solution {
        public int maxDepth(TreeNode root) {
            // 判断 根节点是否为空
            if(root == null) {
                return 0;
            }
            // 建立一个新队列，存每一层的节点
            Queue<TreeNode> queue = new LinkedList<>();
            // 根节点进队列
            queue.offer(root);
            // 深度值计数
            int res = 0;

            // 循环直到队列为空
            while(!queue.isEmpty()) {
                // 这一层队列的个数
                int size = queue.size();
                // 遍历这一层所所有节点并 出队列
                while(size > 0) {
                    // 出队
                    TreeNode node = queue.poll();
                    // 左子树不为空，进队
                    if(node.left != null) {
                        queue.offer(node.left);
                    }
                    // 右子树不为空，进队
                    if(node.right != null) {
                        queue.offer(node.right);
                    }
                    size--;
                }
                // 深度+1
                res++;
            }

            return res;
        }
    }

    /**
     * 2、递归 -- 0ms(100.00%), 41.63MB(11.64%)
     * <p>
     * 时间复杂度：O(n)
     * <p>
     * 空间复杂度：O(n)
     */
    class Solution2 {
        public int maxDepth(TreeNode root) {
            if (root == null) {
                return 0;
            }
            int left = maxDepth(root.left);
            int right = maxDepth(root.right);
            return Math.max(left, right) + 1;
        }
    }

}

