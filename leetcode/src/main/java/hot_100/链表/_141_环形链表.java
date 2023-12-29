package hot_100.链表;

import hot_100.ListNode;

import java.util.HashSet;
import java.util.Set;

/**
 * 环形链表 -- 哈希表、链表、双指针
 *
 * @author 沉梦听雨
 **/
@SuppressWarnings("all")
public class _141_环形链表 {

    /* 给你一个链表的头节点 head ，判断链表中是否有环。
       如果链表中存在环 ，则返回 true 。 否则，返回 false 。 */

    /**
     * 1、快慢指针 -- 0ms(100.00%), 42.80MB(21.80%)
     * <p>
     * 时间复杂度：O(n)
     * <p>
     * 空间复杂度：O(1)
     */
    public class Solution {
        public boolean hasCycle(ListNode head) {
            if (head == null || head.next == null) {
                return false;
            }
            ListNode fast = head.next;
            ListNode slow = head;
            while(fast != null && fast.next != null) {
                fast = fast.next.next;
                slow = slow.next;
                if (fast == slow) return true;
            }
            return false;
        }
    }

    /**
     * 2、利用不重复性（HashSet） -- 4ms(13.84%), 43.90MB(5.03%)
     * <p>
     * 时间复杂度：O(n)
     * <p>
     * 空间复杂度：O(n)
     */
    public class Solution2 {
        public boolean hasCycle(ListNode head) {
            // 创建一个HashSet，用于存储已经访问过的节点
            Set<ListNode> seen = new HashSet<ListNode>();
            while(head != null) {
                // 如果HashSet中已经存在当前节点，则说明有环
                if(!seen.add(head)){
                    return true;
                }
                head = head.next;
            }
            return false;
        }
    }
}
