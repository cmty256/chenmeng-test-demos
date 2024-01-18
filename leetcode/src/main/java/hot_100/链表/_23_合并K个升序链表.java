package hot_100.链表;

import hot_100.ListNode;

import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

/**
 * 合并 K 个升序链表 -- 链表、分治、堆（优先队列）、归并排序
 *
 * @author 沉梦听雨
 **/
@SuppressWarnings("all")
public class _23_合并K个升序链表 {

    /* 给你一个链表数组，每个链表都已经按升序排列。
       请你将所有链表合并到一个升序链表中，返回合并后的链表。 */

    /**
     * 1、优先队列（小根堆） -- 4ms(70.11%), 43.41MB(10.57%)
     * <p>
     * 时间复杂度：O(nlogk)，其中 n 是所有链表节点数目的总和，而 k 是题目给定的链表数目。
     * <p>
     * 空间复杂度：O(k)
     */
    class Solution {
        public ListNode mergeKLists(ListNode[] lists) {
            // 创建一个优先队列 queue，元素为 ListNode，并根据 ListNode 的 val 属性进行排序
            PriorityQueue<ListNode> queue = new PriorityQueue<>((a, b) -> a.val - b.val);

            // 遍历 lists 数组，将每个链表的头节点加入优先队列中
            for (ListNode head : lists) {
                // 如果当前链表的头节点不为空
                if (head != null) {
                    // 将【当前链表的头节点】加入优先队列中，注意：是当前链表的头结点
                    queue.offer(head);
                }
            }

            // 创建一个虚拟的头节点 dummy
            ListNode dummy = new ListNode();
            // 创建一个指针 cur，初始时指向虚拟头节点 dummy
            ListNode cur = dummy;
            // 当优先队列 queue 不为空时
            while (!queue.isEmpty()) {
                // 从优先队列 queue 中取出一个节点 node
                ListNode node = queue.poll();
                // 如果当前节点的下一个节点不为空
                if (node.next != null) {
                    // 将当前节点的下一个节点加入优先队列中
                    queue.offer(node.next);
                }
                // 将当前节点 node 加入到 cur 的下一个节点
                cur.next = node;
                // 将 cur 指针移动到下一个节点
                cur = cur.next;
            }

            // 返回虚拟头节点的下一个节点，即合并后的链表的头节点
            return dummy.next;
        }
    }

}
