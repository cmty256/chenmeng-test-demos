package hot_100.二叉树;

import java.util.HashMap;
import java.util.Map;

/**
 * 从前序与中序遍历序列构造二叉树 -- 树、数组、哈希表、分治、二叉树
 *
 * @author 沉梦听雨
 **/
@SuppressWarnings("all")
public class _105_从前序与中序遍历序列构造二叉树 {

    /* 给定两个整数数组 preorder 和 inorder ，其中 preorder 是二叉树的先序遍历，
       inorder 是同一棵树的中序遍历98, 230, 199 -- 题解提交，请构造二叉树并返回其根节点。 */

    /**
     * 1、哈希表 + 递归 -- 1ms(98.94%), 43.15MB(59.30%)
     * <p>
     * 时间复杂度：O(n)
     * <p>
     * 空间复杂度：O(n)
     */
    class Solution {
        private int[] preorder; // 存储前序遍历的结果
        private Map<Integer, Integer> indexMap = new HashMap<>(); // 存储中序遍历结果的值到索引的映射

        // 输入前序遍历和中序遍历的结果，构建并返回二叉树
        public TreeNode buildTree(int[] preorder, int[] inorder) {
            int length = preorder.length; // 树的节点数量
            this.preorder = preorder; // 存储前序遍历结果到成员变量
            // 存储中序遍历结果的值和索引，映射到 indexMap
            for (int i = 0; i < length; i++) {
                indexMap.put(inorder[i], i);
            }
            // 构建整棵树并返回 -- dfs(i,j,n)，其中之和j分别表示前序序列和中序序列的起始位置，而n表示节点个数
            return buildSubTree(0, 0, length);
        }

        /**
         * 根据前序和中序遍历的结果构建子树
         *
         * @param rootIndexPreorder 在前序遍历序列中当前子树的根节点的索引 -- 前序序列的起始位置
         * @param leftIndexInorder  在中序遍历序列中当前子树的最左节点的索引 -- 中序序列的起始位置
         * @param subTreeSize       当前子树的节点数量
         * @return
         */
        private TreeNode buildSubTree(int rootIndexPreorder, int leftIndexInorder, int subTreeSize) {
            // 子树没有节点，返回 null
            if (subTreeSize <= 0) {
                return null;
            }
            // 找到 根节点的值 和 在中序遍历结果中的索引
            int rootValue = preorder[rootIndexPreorder];
            int rootIndexInorder = indexMap.get(rootValue);
            // 构建左子树
            TreeNode leftSubTree = buildSubTree(rootIndexPreorder + 1,
                    leftIndexInorder,
                    rootIndexInorder - leftIndexInorder);
            // 构建右子树
            TreeNode rightSubTree = buildSubTree(rootIndexPreorder + 1 + rootIndexInorder - leftIndexInorder,
                    rootIndexInorder + 1,
                    subTreeSize - 1 - (rootIndexInorder - leftIndexInorder));
            // 构建当前节点并返回
            return new TreeNode(rootValue, leftSubTree, rightSubTree);
        }
    }

    /**
     * 2、递归 -- 0ms(100.00%), 43.30MB(38.06%)
     * <p>
     * 时间复杂度：O(n)
     * <p>
     * 空间复杂度：O(n)
     */
    class Solution2 {
        // 全局变量 pre 和 in 用于追踪前序遍历和中序遍历的位置
        public int pre = 0;
        public int in = 0;

        // 主方法，入口点，接收前序遍历和中序遍历的结果，返回构建的二叉树的根节点
        public TreeNode buildTree(int[] preorder, int[] inorder) {
            // 调用深度优先搜索方法，并传入 stop 参数作为中序遍历的停止条件
            return dfs(preorder, inorder, 3001);
        }

        // 深度优先搜索方法，根据前序遍历和中序遍历的结果构建二叉树
        public TreeNode dfs(int[] preorder, int[] inorder, int stop) {
            // 如果前序遍历已经遍历完，返回 null
            if (pre == preorder.length) {
                return null;
            }
            // 如果中序遍历的当前节点的值等于停止条件，表示当前子树的左子树已经构建完毕
            if (inorder[in] == stop) {
                in++;
                return null; // 返回 null 表示当前节点的左子树为空
            }
            // 构建当前节点，节点值为前序遍历的当前位置的值
            TreeNode node = new TreeNode(preorder[pre++]);
            // 递归构建左子树，停止条件为当前节点的值
            node.left = dfs(preorder, inorder, node.val);
            // 递归构建右子树，停止条件为传入的停止条件
            node.right = dfs(preorder, inorder, stop);
            // 返回当前节点
            return node;
        }
    }
}

