package hot_100.二叉树;

/**
 * 验证二叉搜索树 -- 树、深度优先搜索、二叉搜索树、二叉树
 *
 * @author 沉梦听雨
 **/
@SuppressWarnings("all")
public class _98_验证二叉搜索树 {

    /* 给你一个二叉树的根节点 root ，判断其是否是一个有效的二叉搜索树。*/

    /**
     * 1、递归 -- 0ms(100.00%), 42.29MB(79.61%)
     * <p>
     * 时间复杂度：O(n)
     * <p>
     * 空间复杂度：O(n)
     */
    class Solution {
        // 定义一个 long 类型的变量 pre，初始值为 Long.MIN_VALUE
        long pre = Long.MIN_VALUE;

        public boolean isValidBST(TreeNode root) {
            // 如果传入的 root 节点为空，则返回 true
            if (root == null) {
                return true;
            }
            // 如果左子树不为空，则递归判断左子树是否为二叉搜索树
            if (!isValidBST(root.left)) {
                return false;
            }
            // 如果当前节点的值小于等于 pre，则返回 false
            if (root.val <= pre) {
                return false;
            }
            // 否则，将当前节点的值赋值给 pre
            pre = root.val;
            // 递归判断右子树是否为二叉搜索树
            return isValidBST(root.right);
        }
    }

    /**
     * 2、递归 -- 0ms(100.00%), 42.05MB(91.85%)
     * <p>
     * 时间复杂度：O(n)
     * <p>
     * 空间复杂度：O(n)
     */
    class Solutio2 {
        public boolean isValidBST(TreeNode root) {
            // 调用dfs函数，传入root节点，以及long类型的最小值和最大值
            return dfs(root, Long.MIN_VALUE, Long.MAX_VALUE);
        }

        // 定义一个dfs函数，用来递归判断node节点是否在给定的范围内
        private boolean dfs(TreeNode root, long l, long r) {
            // 如果node为空，则返回true
            if (root == null) {
                return true;
            }
            // 如果node的值小于等于左边界或者大于等于右边界，则返回false
            if (root.val <= l || root.val >= r) {
                return false;
            }
            // 递归调用dfs函数，传入node的左节点和右节点，以及边界值l和r
            return dfs(root.left, l, root.val) && dfs(root.right, root.val, r);
        }
    }

}

