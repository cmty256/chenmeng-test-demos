package hot_100.链表;

import hot_100.ListNode;

/**
 * 合并两个有序链表 -- 递归、链表
 *
 * @author 沉梦听雨
 **/
@SuppressWarnings("all")
public class _21_合并两个有序链表 {

    /* 将两个升序链表合并为一个新的 升序 链表并返回。
       新链表是通过拼接给定的两个链表的所有节点组成的。 */

    /**
     * 1、迭代（双指针） -- 0ms(100.00%), 41.5MB(5.14%)
     * <p>
     * 时间复杂度：O(m + n), 其中 m 和 n 分别为两个链表的长度。
     * <p>
     * 空间复杂度：O(1)
     */
    class Solution {
        public ListNode mergeTwoLists(ListNode list1, ListNode list2) {
            ListNode res = new ListNode();
            ListNode curr = res;
            // 均不为 空 的情况
            while (list1 != null && list2 != null) {
                if (list1.val <= list2.val) {
                    curr.next = list1;
                    list1 = list1.next;
                } else {
                    curr.next = list2;
                    list2 = list2.next;
                }
                curr = curr.next;
            }
            // 至少有一个为 空 的情况
            curr.next = list1 == null ? list2 : list1;
            return res.next;
        }
    }

    /**
     * 2、递归 -- 0ms(100.00%), 41.62MB(5.14%)
     * <p>
     * 时间复杂度：O(m + n), 其中 m 和 n 分别为两个链表的长度。
     * <p>
     * 空间复杂度：O(m + n)
     */
    class Solution2 {
        public ListNode mergeTwoLists(ListNode list1, ListNode list2) {
            if (list1 == null) {
                return list2;
            } else if (list2 == null) {
                return list1;
            } else if (list1.val < list2.val) {
                list1.next = mergeTwoLists(list1.next, list2);
                return list1;
            } else {
                list2.next = mergeTwoLists(list1, list2.next);
                return list2;
            }
        }
    }

}
