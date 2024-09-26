package hot_100.链表;

import java.util.HashMap;
import java.util.Map;

/**
 * 随机链表的复制 -- 哈希表、链表
 *
 * @author chenmeng
 **/
@SuppressWarnings("all")
public class _138_随机链表的复制 {

    /* 实现一个复制带有随机指针的链表。 */

    /**
     * 1、哈希表 -- 0ms(100.00%), 43.09MB(26.26%)
     * <p>
     * 时间复杂度：O(n)
     * <p>
     * 空间复杂度：O(n)
     */
    class Solution {
        public Node copyRandomList(Node head) {
            // 如果原链表头节点为空，返回空
            if (head == null) {
                return null;
            }

            // 创建当前节点 cur，并初始化为原链表头节点
            Node cur = head;
            // 创建一个映射表 map，用于存储原链表节点和新链表节点的对应关系
            Map<Node, Node> map = new HashMap<>();

            // 第一遍循环，创建新链表节点并将其存入映射表
            while (cur != null) {
                // 将当前节点 cur 作为 key，创建一个值为当前节点值的新节点作为 value 放入映射表
                map.put(cur, new Node(cur.val));
                // 将当前节点指针 cur 移动到下一个节点
                cur = cur.next;
            }

            // 将当前节点 cur 重新指向原链表头节点
            cur = head;
            // 第二遍循环，连接新链表的 next 和 random 指针
            while (cur != null) {
                // 通过映射表获取当前节点 cur 对应的新节点，然后设置新节点的 next 和 random 指针
                map.get(cur).next = map.get(cur.next);
                map.get(cur).random = map.get(cur.random);
                // 将当前节点指针 cur 移动到下一个节点
                cur = cur.next;
            }

            // 返回新链表的头节点
            return map.get(head);
        }
    }

    /**
     * 2、拼接 + 拆分 -- 0ms(100.00%), 43.25MB(17.02%)
     * <p>
     * 时间复杂度：O(n)
     * <p>
     * 空间复杂度：O(1)
     */
    class Solution2 {
        public Node copyRandomList(Node head) {
            // 如果原链表头节点为空，返回空
            if (head == null) {
                return null;
            }

            // 第一遍循环，在每个节点后面插入一个相同值的新节点
            Node cur = head;
            while (cur != null) {
                // 创建一个新节点 tmp，值为当前节点的值
                Node tmp = new Node(cur.val);
                // 将新节点的 next 指针指向当前节点的下一个节点
                tmp.next = cur.next;
                // 将当前节点的 next 指针指向新节点
                cur.next = tmp;
                // 将当前节点指针 cur 移动到下一个节点的下一个节点
                cur = tmp.next;
            }

            // 第二遍循环，连接新节点的 random 指针
            cur = head;
            while (cur != null) {
                // 如果当前节点的 random 指针不为空，将新节点的 random 指针指向当前节点 random 指针的下一个节点
                if (cur.random != null) {
                    cur.next.random = cur.random.next;
                }
                // 将当前节点指针 cur 移动到下一个节点的下一个节点
                cur = cur.next.next;
            }

            // 第三遍循环，分离新旧链表
            cur = head.next;
            Node pre = head, res = head.next;
            while (cur.next != null) {
                // 将原链表的 next 指针指向下一个节点的下一个节点
                pre.next = pre.next.next;
                // 将新链表的 next 指针指向下一个节点的下一个节点
                cur.next = cur.next.next;
                // 将原链表和新链表的指针分别移动到下一个节点
                pre = pre.next;
                cur = cur.next;
            }
            // 将原链表的尾部节点的 next 指针置为空
            pre.next = null;
            // 返回新链表的头节点
            return res;
        }
    }

}
