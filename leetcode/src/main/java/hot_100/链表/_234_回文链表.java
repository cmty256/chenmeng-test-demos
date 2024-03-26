package hot_100.链表;

import hot_100.ListNode;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 回文链表 -- 栈、递归、链表、双指针
 *
 * @author cmty256
 **/
@SuppressWarnings("all")
public class _234_回文链表 {

    /* 给你两个单链表的头节点 headA 和 headB ，
       请你找出并返回两个单链表相交的起始节点。
       如果两个链表不存在相交节点，返回 null 。 */

    /**
     * 1、遍历 + 数组 -- 4ms(79.41%), 55.22MB(84.99%)
     * <p>
     * 时间复杂度：O(n)
     * <p>
     * 空间复杂度：O(n)
     */
    class Solution {
        public boolean isPalindrome(ListNode head) {
            // 空链表或仅包含一个元素的链表无论如何都是回文的（题目描述必定不为空）
            // if (head == null || head.next == null) {
            //     return true;
            // }
            int len = 0;
            ListNode p = head;
            // 得出链表长度
            while (p != null) {
                len++;
                p = p.next;
            }
            // 定义一个与链表一样长的数组
            int[] arr = new int[len];
            // 重新定义一下链表，因为上面定义的已经为空
            p = head;
            for (int i = 0; i < len; i++) {
                arr[i] = p.val;
                p = p.next;
            }
            for (int i = 0; i < len / 2; i++) {
                if (arr[i] != arr[len - 1 - i]) {
                    return false;
                }
            }
            return true;
        }
    }

    /**
     * 2、遍历 + 列表 -- 8ms(37.68%), 54.37MB(97.42%)
     * <p>
     * 时间复杂度：O(n)
     * <p>
     * 空间复杂度：O(n)
     */
    class Solution2 {
        public boolean isPalindrome(ListNode head) {
            List<Integer> list = new ArrayList<>();
            while (head != null) {
                list.add(head.val);
                head = head.next;
            }
            int mid = list.size() >> 1;
            for (int left = 0; left < mid; left++) {
                if (list.get(left) != list.get(list.size() - 1 - left)) {
                    return false;
                }
            }
            return true;
        }
    }

    /**
     * 3、快慢指针 + 反转链表 -- 4ms(79.41%), 67.43MB(5.6%)
     * <p>
     * 时间复杂度：O(n)
     * <p>
     * 空间复杂度：O(1)
     */
    class Solution3 {
        public boolean isPalindrome(ListNode head) {
            if (head == null || head.next == null) {
                return true;
            }

            // 使用快慢指针找到链表的中点
            ListNode slow = head;
            ListNode fast = head;
            while (fast.next != null && fast.next.next != null) {
                slow = slow.next;
                fast = fast.next.next;
            }

            // 反转链表的后半部分
            ListNode secondHalf = reverseList(slow.next);
            slow.next = null;

            // 比较前半部分和反转后的后半部分
            ListNode p1 = head;
            ListNode p2 = secondHalf;
            while (p1 != null && p2 != null) {
                if (p1.val != p2.val) {
                    return false;
                }
                p1 = p1.next;
                p2 = p2.next;
            }

            return true;
        }

        private ListNode reverseList(ListNode head) {
            ListNode prev = null;
            ListNode current = head;
            while (current != null) {
                ListNode nextNode = current.next;
                current.next = prev;
                prev = current;
                current = nextNode;
            }
            return prev;
        }
    }
}
