package hot_100.二叉树;

import java.util.*;

/**
 * 二叉树的右视图 -- 树、深度优先搜索、广度优先搜索、二叉树
 *
 * @author chenmeng
 **/
@SuppressWarnings("all")
public class _199_二叉树的右视图 {

    /* 给定一个二叉树的 根节点 root，想象自己站在它的右侧，
       按照从顶部到底部的顺序，返回从右侧所能看到的节点值。*/

    /**
     * 1、广度优先搜索 -- 1ms(83.09%), 41.35MB(5.23%)
     * <p>
     * 时间复杂度：O(n)
     * <p>
     * 空间复杂度：O(n)
     */
    class Solution {
        public List<Integer> rightSideView(TreeNode root) {
            // 如果根节点为空，返回一个空的整数列表
            if (root == null) {
                return new ArrayList<Integer>();
            }
            // 定义一个结果列表，用来存放右视图的节点值
            List<Integer> result = new ArrayList<>();
            // 定义一个队列，用来存储树的节点
            Queue<TreeNode> queue = new LinkedList<>();

            // 将根节点添加到队列中
            queue.add(root);
            // 当队列不为空时，进行循环
            while (!queue.isEmpty()) {
                // 获取队列的大小
                int size = queue.size();

                // 遍历队列中的节点
                for (int i = 0; i < size; i++) {
                    // 从队列中取出当前节点
                    TreeNode currentNode = queue.poll();

                    // 如果当前节点是当前层的最后一个节点，将其值添加到结果列表中
                    if (i == size - 1) {
                        result.add(currentNode.val);
                    }

                    // 如果当前节点的左子节点不为空，将其添加到队列中
                    if (currentNode.left != null) {
                        queue.add(currentNode.left);
                    }
                    // 如果当前节点的右子节点不为空，将其添加到队列中
                    if (currentNode.right != null) {
                        queue.add(currentNode.right);
                    }
                }
            }

            return result;
        }
    }

    /**
     * 2、深度优先搜索 -- 0ms(100.00%), 41.14MB(15.11%)
     * <p>
     * 时间复杂度：O(n)
     * <p>
     * 空间复杂度：O(n)
     */
    class Solution2 {
        private List<Integer> res = new ArrayList<>();

        public List<Integer> rightSideView(TreeNode root) {
            dfs(root, 0);
            return res;
        }

        private void dfs(TreeNode node, int depth) {
            if (node == null) {
                return;
            }
            if (depth == res.size()) {
                res.add(node.val);
            }
            dfs(node.right, depth + 1);
            dfs(node.left, depth + 1);
        }
    }
}

