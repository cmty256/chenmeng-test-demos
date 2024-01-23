package hot_100.二叉树;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

/**
 * 将有序数组转换为二叉搜索树 -- 树、二叉搜索树、数组、分治、二叉树
 *
 * @author 沉梦听雨
 **/
@SuppressWarnings("all")
public class _108_将有序数组转换为二叉搜索树 {

    /* 给你一个整数数组 nums ，其中元素已经按 升序 排列，
       请你将其转换为一棵 高度平衡 二叉搜索树。
       高度平衡 二叉树是一棵满足「每个节点的左右两个子树的高度差的绝对值不超过 1 」的二叉树。*/

    /**
     * 1、二分 + 递归 -- 0ms(100.00%), 41.91MB(88.88%)
     * <p>
     * 时间复杂度：O(n)
     * <p>
     * 空间复杂度：O(logn)
     */
    class Solution {
        public TreeNode sortedArrayToBST(int[] nums) {
            return dfs(nums, 0, nums.length - 1);
        }

        private TreeNode dfs(int[] nums, int start, int end) {
            if (start > end) {
                return null;
            }
            // 以升序数组的中间元素作为根节点 root
            // start + (end - start) / 2 == (start + end) >> 1
            int mid = (start + end) >> 1;
            TreeNode root = new TreeNode(nums[mid]);
            // 递归的构建 root 的左子树与右子树
            root.left = dfs(nums, start, mid - 1);
            root.right = dfs(nums, mid + 1, end);
            // 返回构建好的根节点 root
            return root;
        }
    }

}

