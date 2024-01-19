package hot_100.二叉树;

import java.util.*;

/**
 * LRU缓存 -- 设计、哈希表、链表、双向链表
 *
 * @author 沉梦听雨
 **/
@SuppressWarnings("all")
public class _94_二叉树的中序遍历 {

    /* 给定一个二叉树的根节点 root ，返回 它的 中序 遍历（左中右） 。*/

    /**
     * 1、递归 -- 0ms(100.00%), 40.80MB(5.08%)
     * <p>
     * 时间复杂度：O(n)
     * <p>
     * 空间复杂度：O(n)
     */
    class Solution {
        public List<Integer> inorderTraversal(TreeNode root) {
            // 新建一个存放遍历结果的集合
            List<Integer> res = new ArrayList<>();
            // 左根右排序 遍历
            midorder(root, res);

            // 返回遍历结果
            return res;
        }

        public void midorder(TreeNode root, List<Integer> res) {
            // 判断到空节点，直接断开
            if (root == null) {
                return;
            }
            // 递归左子树
            midorder(root.left, res);
            // 存值
            res.add(root.val);
            // 递归右子树
            midorder(root.right, res);
        }
    }

    /**
     * 2、栈 -- 0ms(100.00%), 40.73MB(5.29%)
     * <p>
     * 时间复杂度：O(n)
     * <p>
     * 空间复杂度：O(n)
     */
    class Solution2 {
        public List<Integer> inorderTraversal(TreeNode root) {
            // 创建结果列表，用于存储中序遍历后的值
            List<Integer> res = new ArrayList<>();
            // 创建一个栈，用于存储遍历过程中的节点
            Stack<TreeNode> stack = new Stack<>();

            // 当根节点不为空或者栈不为空时，循环继续
            while (root != null || !stack.isEmpty()) {
                // 如果根节点不为空
                if (root != null) {
                    // 将根节点入栈
                    stack.push(root);
                    // 继续遍历左子树
                    root = root.left;
                    // 否则，弹出栈顶节点，将其值添加到列表中，遍历右子树
                } else {
                    // 弹出栈顶节点
                    root = stack.pop();
                    // 将栈顶节点的值添加到列表中
                    res.add(root.val);
                    // 遍历右子树
                    root = root.right;
                }
            }

            // 返回中序遍历后的列表
            return res;
        }
    }

    /**
     * 3、Morris(莫里斯) 实现中序遍历 -- 0ms(100.00%), 40.73MB(5.29%)
     * <p>
     * 时间复杂度：O(n)
     * <p>
     * 空间复杂度：O(1)
     */
    class Solution3 {
        public List<Integer> inorderTraversal(TreeNode root) {
            // 创建结果列表，存放中序遍历的结果
            List<Integer> res = new ArrayList<>();

            // 有根节点时，进行循环
            while (root != null) {
                // 如果根节点的左子节点为空，将根节点的值添加到列表中，然后将右子节点作为新的根节点
                if (root.left == null) {
                    res.add(root.val);
                    root = root.right;
                } else {
                    // 否则，找到根节点左子树的最右子节点（中序遍历在根节点之前的节点）
                    TreeNode prev = root.left;
                    while (prev.right != null && prev.right != root) {
                        prev = prev.right;
                    }

                    // 如果最右子节点的右子节点为空，将其右子节点设置为当前根节点，然后将左子节点作为新的根节点
                    if (prev.right == null) {
                        prev.right = root;
                        root = root.left;
                    } else {
                        // 否则（即最右子节点的右子节点为当前根节点），将根节点的值添加到列表中，将最右子节点的右子节点设为空，然后将右子节点作为新的根节点
                        res.add(root.val);
                        prev.right = null;
                        root = root.right;
                    }
                }
            }

            // 返回中序遍历的结果
            return res;
        }
    }

}

