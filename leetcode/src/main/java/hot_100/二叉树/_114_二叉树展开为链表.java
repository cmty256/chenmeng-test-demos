package hot_100.二叉树;

/**
 * 二叉树展开为链表 -- 栈、树、深度优先搜索、链表、二叉树
 *
 * @author cmty256
 **/
@SuppressWarnings("all")
public class _114_二叉树展开为链表 {

    /* 给你二叉树的根结点 root ，请你将它展开为一个单链表 */

    /**
     * 1、寻找前驱节点 -- 0ms(100.00%), 41.00MB(55.78%)
     * <p>
     * 时间复杂度：O(n)
     * <p>
     * 空间复杂度：O(1)
     */
    class Solution {
        public void flatten(TreeNode root) {
            while (root != null) {
                if (root.left != null) {
                    // 找到当前节点左子树的最右节点
                    TreeNode pre = root.left;
                    while (pre.right != null) {
                        pre = pre.right;
                    }

                    // 将左子树的最右节点（前驱节点）指向原来的右子树
                    pre.right = root.right;

                    // 将当前节点的右节点指向左子树
                    root.right = root.left;
                    root.left = null;
                }
                root = root.right;
            }
        }
    }
}

