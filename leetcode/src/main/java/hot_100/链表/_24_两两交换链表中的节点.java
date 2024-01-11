package hot_100.链表;

import hot_100.ListNode;

/**
 * 两两交换链表中的节点 -- 递归、链表
 *
 * @author 沉梦听雨
 **/
@SuppressWarnings("all")
public class _24_两两交换链表中的节点 {

    /* 给你一个链表，两两交换其中相邻的节点，并返回交换后链表的头节点。
       你必须在不修改节点内部的值的情况下完成本题（即，只能进行节点交换）。 */

    /**
     * 1、递归 -- 0ms(100.00%), 40.40MB(5.13%)
     * <p>
     * 时间复杂度：O(n)
     * <p>
     * 空间复杂度：O(n)
     */
    class Solution {
        public ListNode swapPairs(ListNode head) {
            // 如果链表为空或只有一个节点，直接返回头节点，不需要进行交换
            if (head == null || head.next == null) {
                return head;
            }

            // 递归调用swapPairs函数，处理剩余的节点，传入头【节点的下一个节点的下一个节点】，返回值赋值给tmp
            ListNode tmp = swapPairs(head.next.next);
            // 获取当前节点的下一个节点赋值给p
            ListNode p = head.next;

            // 将下一个节点的next指针指向当前节点，完成交换
            p.next = head;
            // 将当前节点的next指针指向递归调用的结果，继续处理剩余的节点
            head.next = tmp;

            // 返回交换后的头节点
            return p;
        }
    }

    /**
     * 2、虚拟头节点 + 双指针 -- 0ms(100.00%), 39.89MB(19.62%)
     * <p>
     * 时间复杂度：O(n)
     * <p>
     * 空间复杂度：O(1)
     */
    class Solution2 {
        public ListNode swapPairs(ListNode head) {
            // 创建虚拟头节点，并将其 next 指向原始链表的头节点
            ListNode dummy = new ListNode(0, head);
            // 创建两个指针 pre 和 cur，将 pre 初始化为虚拟头节点
            ListNode pre = dummy;
            // 将 cur 初始化为原始链表的头节点而不是虚拟头节点
            ListNode cur = head;

            // 循环条件：当前节点 cur 和它的下一个节点 cur.next 均不为 null, 不为 null 才能进行交换
            while (cur != null && cur.next != null) {
                // 创建临时节点 tmp，保存下一个节点的引用
                ListNode tmp = cur.next;

                // 将当前节点 cur 的 next 指针指向下一个节点的下一个节点，跳过下一个节点
                cur.next = tmp.next;
                // 将临时节点 tmp 的 next 指针指向当前节点 cur，完成交换
                tmp.next = cur;
                // 将前一个节点 pre 的 next 指针指向交换后的新节点 tmp
                pre.next = tmp;

                // 将 pre 移动到下一对相邻节点的前一个节点，即当前节点 cur
                pre = cur;
                // 将 cur 移动到下一对相邻节点的当前节点，即下一个节点
                cur = cur.next;
            }

            // 返回虚拟头节点 dummy 的 next，即完成相邻节点的交换后的链表
            return dummy.next;
        }
    }
}
