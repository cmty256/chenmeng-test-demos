package hot_100.链表;

import hot_100.ListNode;

/**
 * 环形链表 II -- 哈希表、链表、双指针
 *
 * @author cmty256
 **/
@SuppressWarnings("all")
public class _142_环形链表II {

    /* 给定一个链表的头节点 head，返回链表开始入环的第一个节点。*/

    /**
     * 1、快慢指针 -- 0ms(100.00%), 43.27MB(7.83%)
     * <p>
     * 时间复杂度：O(n)
     * <p>
     * 空间复杂度：O(1)
     */
    public class Solution {
        public ListNode detectCycle(ListNode head) {
            // 快慢指针，快指针每次移动两步，慢指针每次移动一步
            ListNode fast = head, slow = head;
            // 判断快慢指针是否相遇
            while (fast != null && fast.next != null) {
                // 慢指针每次移动一步
                slow = slow.next;
                // 快指针每次移动两步
                fast = fast.next.next;
                // 如果相遇，则表示有环
                if (slow == fast) {
                    // 找到入环点
                    ListNode res = head;
                    // 判断入环点
                    while (res != slow) {
                        res = res.next;
                        slow = slow.next;
                    }
                    return res;
                }
            }
            return null;
        }
    }
}
