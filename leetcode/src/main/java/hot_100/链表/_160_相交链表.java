package hot_100.链表;

import hot_100.ListNode;

import java.util.HashSet;
import java.util.Set;

/**
 * 相交链表 -- 哈希表、链表、双指针
 *
 * @author chenmeng
 **/
@SuppressWarnings("all")
public class _160_相交链表 {

    /* 给你两个单链表的头节点 headA 和 headB ，
       请你找出并返回两个单链表相交的起始节点。
       如果两个链表不存在相交节点，返回 null 。 */

    /**
     * 1、遍历 + HashSet -- 6ms(13.58%), 46.7MB(5.00%)
     * <p>
     * 时间复杂度：O(m + n), 其中 m 和 n 分别是 headA 和 headB 的长度。
     * <p>
     * 空间复杂度：O(m), HashSet 存储的是 headA 的所有节点
     */
    public class Solution {
        public ListNode getIntersectionNode(ListNode headA, ListNode headB) {
            Set<ListNode> hashSet = new HashSet<>();
            // 先遍历A链表,并把A链表的所有节点加入到哈希集合中
            ListNode temp = headA;
            while (temp != null) {
                hashSet.add(temp);
                temp = temp.next;
            }
            // 再遍历B链表
            temp = headB;
            while (temp != null) {
                // 判断集合中是否包含B链表的此节点
                if (hashSet.contains(temp)) {
                    // 包含，返回相交节点
                    return temp;
                }
                // 不包含，遍历下一个节点
                temp = temp.next;
            }
            // 没有相交节点，返回null
            return null;
        }
    }

    /**
     * 2、双指针 -- 1ms(99.09%), 47.42MB(5.00%)
     * <p>
     * 时间复杂度：O(m + n), 其中 m 和 n 分别是 headA 和 headB 的长度。
     * <p>
     * 空间复杂度：O(1)
     */
    public class Solution2 {
        public ListNode getIntersectionNode(ListNode headA, ListNode headB) {
            ListNode a = headA, b = headB;
            while (a != b) {
                a = a == null ? headB : a.next;
                b = b == null ? headA : b.next;
            }
            return a;
        }
    }

}
