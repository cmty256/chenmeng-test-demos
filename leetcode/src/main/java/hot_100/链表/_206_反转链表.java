package hot_100.链表;

import hot_100.ListNode;

import java.util.HashSet;
import java.util.Set;

/**
 * 相交链表 -- 递归、链表
 *
 * @author cmty256
 **/
@SuppressWarnings("all")
public class _206_反转链表 {

    /* 给你两个单链表的头节点 headA 和 headB ，
       请你找出并返回两个单链表相交的起始节点。
       如果两个链表不存在相交节点，返回 null 。 */

    /**
     * 1、迭代（双指针） -- 0ms(100.00%), 41.18MB(5.01%)
     * <p>
     * 时间复杂度：O(n), 其中 n 为链表的长度。
     * <p>
     * 空间复杂度：O(1)
     */
    class Solution {
        public ListNode reverseList(ListNode head) {
            // 假设最前面是个null
            ListNode prev = null;
            // 当前节点，初始值为头节点。
            ListNode curr = head;

            // 遍历链表，每遍历一次就转换一次箭头
            while (curr != null) {
                // 存储当前节点（头节点）的下一节点
                ListNode next = curr.next;
                // null（prev）->curr 变为 null（prev）<-curr，转换箭头
                curr.next = prev;
                // 前置指针后移一位
                prev = curr;
                // 后置指针后移一位
                curr = next;
            }
            return prev;
        }
    }

    /**
     * 2、递归 -- 0ms(100.00%), 41.30MB(5.01%)
     * <p>
     * 时间复杂度：O(n), 其中 n 为链表的长度。
     * <p>
     * 空间复杂度：O(n)
     */
    class Solution2 {
        public ListNode reverseList(ListNode head) {
            // 空链表 或者 尾结点
            if(head == null || head.next == null) {
                return head;
            }
            // 递归
            ListNode res = reverseList(head.next);
            // 反转箭头
            head.next.next = head;
            // 每一节点指向null，把null放最后
            head.next = null;

            return res;
        }
    }

}
