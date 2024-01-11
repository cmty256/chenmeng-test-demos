package hot_100.链表;

import hot_100.ListNode;

import java.util.ArrayList;
import java.util.List;

/**
 * 删除链表的倒数第N个结点 -- 链表、双指针
 *
 * @author 沉梦听雨
 **/
@SuppressWarnings("all")
public class _19_删除链表的倒数第N个结点 {

    /* 给你一个链表，删除链表的倒数第 n 个结点，并且返回链表的头结点。 */

    /**
     * 1、虚拟头节点 + 快慢指针 -- 0ms(100.00%), 40.54MB(12.92%)
     * <p>
     * 时间复杂度：O(n)
     * <p>
     * 空间复杂度：O(1)
     */
    class Solution {
        public ListNode removeNthFromEnd(ListNode head, int n) {
            // 创建一个虚拟节点，指向头节点 -- 即 dummy 为新的头节点
            ListNode dummy = new ListNode(0, head);
            // 快慢指针，快指针先走n步
            ListNode fast = dummy, slow = dummy;
            // 1、快指针先走n步
            while (n-- > 0) {
                fast = fast.next;
            }
            // 2、快慢指针同时移动，当快指针指向最后一个节点时，慢指针指向倒数第n个节点的前一个节点
            while (fast.next != null) {
                slow = slow.next;
                fast = fast.next;
            }
            // 3、删除倒数第n个节点
            slow.next = slow.next.next;
            // 返回头节点
            return dummy.next;
        }
    }

}
