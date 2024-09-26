package hot_100.链表;

import hot_100.ListNode;

/**
 * K个一组翻转链表 -- 递归、链表
 *
 * @author chenmeng
 **/
@SuppressWarnings("all")
public class _25_K个一组翻转链表 {

    /* 给你链表的头节点 head ，每 k 个节点一组进行翻转，请你返回修改后的链表。
       k 是一个正整数，它的值小于或等于链表的长度。
       如果节点总数不是 k 的整数倍，那么请将最后剩余的节点保持原有顺序。
       你不能只是单纯的改变节点内部的值，而是需要实际进行节点交换。 */

    /**
     * 1、虚拟头节点 + 双指针 -- 0ms(100.00%), 43.05MB(14.82%)
     * <p>
     * 时间复杂度：O(n)
     * <p>
     * 空间复杂度：O(1)
     */
    class Solution {
        public ListNode reverseKGroup(ListNode head, int k) {
            // 创建虚拟头节点
            ListNode dummy = new ListNode(0, head);
            // 初始化两个指针 pre 和 cur，均指向虚拟头节点
            ListNode pre = dummy, cur = dummy;
            // 循环遍历链表
            while (cur.next != null) {
                // 将 cur 指针移动 k 次
                for (int i = 0; i < k; i++) {
                    cur = cur.next;
                    // 如果 cur 为 null，说明剩余节点不足 k 个，直接返回虚拟头节点的下一个节点
                    if (cur == null) {
                        return dummy.next;
                    }
                }
                // 记录当前 cur 指针的下一个节点
                ListNode tmp = cur.next;
                // 将 cur 指针的 next 置为 null，断开当前 k 个节点的链表
                cur.next = null;
                // 获取当前 k 个节点的起始节点
                ListNode start = pre.next;
                // 将 pre 节点的 next 指向反转后的链表
                pre.next = reverseList(start);
                // 将反转后的链表的尾节点的 next 指向 tmp，重新连接链表
                start.next = tmp;
                // 更新 pre 和 cur 指针位置
                pre = start;
                cur = pre;
            }
            // 返回虚拟头节点的下一个节点，即整个链表的头节点
            return dummy.next;
        }

        /**
         * 反转链表
         *
         * @param head
         * @return
         */
        private ListNode reverseList(ListNode head) {
            // 初始化前驱节点 pre 为 null
            ListNode pre = null;
            // 当前节点 left 指向链表头节点
            ListNode left = head;
            // 循环遍历链表
            while (left != null) {
                // 临时节点 right 保存当前节点的下一个节点
                ListNode right = left.next;
                // 将当前节点的 next 指针反转，指向前驱节点 pre
                left.next = pre;
                // 将前驱节点 pre 更新为当前节点 left
                pre = left;
                // 将当前节点指针 left 移动到下一个节点
                left = right;
            }
            // 返回反转后的链表头节点
            return pre;
        }
    }
}
