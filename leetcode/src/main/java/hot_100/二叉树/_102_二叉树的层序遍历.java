package hot_100.二叉树;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

/**
 * 二叉树的层序遍历 -- 树、广度优先搜索、二叉树
 *
 * @author 沉梦听雨
 **/
@SuppressWarnings("all")
public class _102_二叉树的层序遍历 {

    /* 给你二叉树的根节点 root, 返回其节点值的 层序遍历（即逐层地，从左到右访问所有节点）。*/

    /**
     * 1、队列实现层序遍历 -- 1ms(92.39%), 43.83MB(16.72%)
     * <p>
     * 时间复杂度：O(n)
     * <p>
     * 空间复杂度：O(n)
     */
    class Solution {
        public List<List<Integer>> levelOrder(TreeNode root) {
            List<List<Integer>> res = new ArrayList<>();
            if (root == null) {
                return res;
            }
            Queue<TreeNode> queue = new ArrayDeque<>();
            queue.offer(root);

            while (!queue.isEmpty()) {
                int size = queue.size();
                List<Integer> ele = new ArrayList<>();

                while (size > 0) {
                    TreeNode cur = queue.poll();
                    ele.add(cur.val);
                    if (cur.left != null) {
                        queue.offer(cur.left);
                    }
                    if (cur.right != null) {
                        queue.offer(cur.right);
                    }
                    size--;
                }

                res.add(ele);
            }

            return res;
        }
    }

    /**
     * 2、递归 -- 0ms(100.00%), 44.04MB(5.15%)
     * <p>
     * 时间复杂度：O(n)
     * <p>
     * 空间复杂度：O(n)
     */
    class Solution2 {
        List<List<Integer>> res = new ArrayList<>();

        public List<List<Integer>> levelOrder(TreeNode root) {
            dfs(root, 0);
            return res;
        }

        public void dfs(TreeNode node, int deep) {
            if (node == null) {
                return;
            }
            if (res.size() < deep + 1) {
                res.add(new ArrayList<>());
            }
            res.get(deep).add(node.val);
            dfs(node.left, deep + 1);
            dfs(node.right, deep + 1);
        }
    }

}

