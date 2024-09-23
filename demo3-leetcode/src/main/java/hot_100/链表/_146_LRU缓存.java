package hot_100.链表;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * LRU缓存 -- 设计、哈希表、链表、双向链表
 *
 * @author chenmeng
 **/
@SuppressWarnings("all")
public class _146_LRU缓存 {

    /* 请你设计并实现一个满足 LRU (最近最少使用) 缓存约束的数据结构。*/

    /**
     * 1、继承哈希链表 -- 44ms(66.36%), 108.75MB(55.79%)
     * <p>
     * 时间复杂度：O(1)
     * <p>
     * 空间复杂度：O(capacity)
     */
    class LRUCache extends LinkedHashMap<Integer, Integer> {
        private int capacity;

        public LRUCache(int capacity) {
            super(capacity, 0.75F, true);
            this.capacity = capacity;
        }

        public int get(int key) {
            return super.getOrDefault(key, -1);
        }

        // 这个可不写
        public void put(int key, int value) {
            super.put(key, value);
        }

        @Override
        protected boolean removeEldestEntry(Map.Entry<Integer, Integer> eldest) {
            return size() > capacity;
        }
    }

    /**
     * 2、哈希表 + 双向链表 -- 48ms(45.72%), 110.32MB(48.13%)
     * <p>
     * 时间复杂度：O(1
     // 定义一个类 Solution2
     class Solution2 {
         // 定义一个方法，返回中序遍历二叉搜索树后的列表
         public List<Integer> inorderTraversal(TreeNode root) {
             // 创建一个列表，用于存储中序遍历后的值
             List<Integer> ans = new ArrayList<>();
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
                     ans.add(root.val);
                     // 遍历右子树
                     root = root.right;
                 }
             }
             // 返回中序遍历后的列表
             return ans;
         }
     }
 / 将根节点的左子节点赋值给根节点
                     root = root.left;
                 //
     // 完整代码
     class Solution3 {
         // 创建一个列表，用于存放中序遍历的结果
         public List<Integer> inorderTraversal(TreeNode root) {
             // 创建一个列表，用于存放中序遍历的结果
             List<Integer> ans = new ArrayList<>();
             // 当根节点不为空时，进行循环
             while (root != null) {
                 // 如果根节点的左子节点为空，将根节点的值添加到列表中，然后遍历根节点的右子节点
                 if (root.left == null) {
                     ans.add(root.val);
                     root = root.right;
                 } else {
                     // 否则，找到根节点左子节点的最右子节点
                     TreeNode prev = root.left;
                     while (prev.right != null && prev.right != root) {
                         prev = prev.right;
                     }
                     // 如果最右子节点为空，说明根节点的左子节点没有右子节点，将根节点的值添加到列表中，然后遍历根节点的右子节点
                     if (prev.right == null) {
                         prev.right = root;
                         root = root.left;
                     } else {
                         // 否则，将根节点的值添加到列表中，然后将最右子节点的右子节点设置为空，遍历根节点的右子节点
                         ans.add(root.val);
                         prev.right = null;
                         root = root.right;
                     }
                 }
             }
             // 返回中序遍历的结果
             return ans;
         }
     }
     } else {
                Node node = new Node(key, value);
                cache.put(key, node);
                addToHead(node);
                ++size;
                if (size > capacity) {
                    node = removeTail();
                    cache.remove(node.key);
                    --size;
                }
            }
        }

        private void moveToHead(Node node) {
            removeNode(node);
            addToHead(node);
        }

        private void removeNode(Node node) {
            node.prev.next = node.next;
            node.next.prev = node.prev;
        }

        private void addToHead(Node node) {
            node.next = head.next;
            node.prev = head;
            head.next = node;
            node.next.prev = node;
        }

        private Node removeTail() {
            Node node = tail.prev;
            removeNode(node);
            return node;
        }
    }

    /**
     * Your LRUCache object will be instantiated and called as such:
     * LRUCache obj = new LRUCache(capacity);
     * int param_1 = obj.get(key);
     * obj.put(key,value);
     */
}
