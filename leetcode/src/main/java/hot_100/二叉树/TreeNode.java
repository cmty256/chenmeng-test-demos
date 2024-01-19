package hot_100.二叉树;

/**
 * 二叉树节点的定义
 * Definition for a binary tree node.
 *
 * @author 沉梦听雨
 */
public class TreeNode {
    int val;
    TreeNode left;
    TreeNode right;

    TreeNode() {
    }

    TreeNode(int val) {
        this.val = val;
    }

    TreeNode(int val, TreeNode left, TreeNode right) {
        this.val = val;
        this.left = left;
        this.right = right;
    }
}