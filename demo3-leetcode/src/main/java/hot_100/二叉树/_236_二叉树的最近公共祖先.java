package hot_100.二叉树;

import java.util.HashMap;
import java.util.Map;

/**
 * 二叉树的最近公共祖先 -- 树、深度优先搜索、二叉树
 *
 * @author chenmeng
 */
@SuppressWarnings("all")
class _236_二叉树的最近公共祖先 {

    /* 给定一个二叉树, 找到该树中两个指定节点的最近公共祖先。
       百度百科中最近公共祖先的定义为：“对于有根树 T 的两个节点 p、q，最近公共祖先表示为一个节点 x，满足 x 是 p、q 的祖先且 x 的深度尽可能大（一个节点也可以是它自己的祖先）。 */

    /**
     * 递归
     */
    class Solution {
        public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
            // 终止条件：如果当前节点为空，或者当前节点是p或q中的一个，则直接返回当前节点
            if (root == null || root == p || root == q) {
                return root;
            }

            // 递归左子树：递归查找左子树中是否存在p或q的公共祖先
            var left = lowestCommonAncestor(root.left, p, q);

            // 递归右子树：递归查找右子树中是否存在p或q的公共祖先
            var right = lowestCommonAncestor(root.right, p, q);

            // 如果左右子树都找到了非空结果，说明当前节点就是它们的公共祖先
            if (left != null && right != null) {
                return root;
            }

            // 如果只有其中一个子树返回了非空结果，则说明p和q都在该子树中，返回对应的子树结果
            // 如果两边都是null，则返回null表示没有找到公共祖先节点
            return left == null ? right : left;
        }
    }

}
