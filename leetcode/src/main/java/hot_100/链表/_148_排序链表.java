package hot_100.链表;

import hot_100.ListNode;

import java.util.*;

/**
 * 排序链表 -- 链表、双指针、分治、排序、归并排序
 *
 * @author 沉梦听雨
 **/
@SuppressWarnings("all")
public class _148_排序链表 {

    /* 给你链表的头结点 head,
       请将其按 升序 排列并返回 排序后的链表 */

    /**
     * 1、自顶向下的归并排序 -- 11ms(59.03%), 55.63MB(18.42%)
     * <p>
     * 时间复杂度：O(nlogn)
     * <p>
     * 空间复杂度：O(n)
     */
    public class Solution {
        public ListNode sortList(ListNode head) {
            if (head == null || head.next == null) {
                return head;
            }

            // 使用快慢指针找到中间节点
            ListNode middle = findMiddle(head);

            // 递归调用自己 -- 将链表分为两部分，分别排序
            ListNode left = sortList(head);
            ListNode right = sortList(middle);

            // 合并两个有序链表
            return merge(left, right);
        }

        private ListNode findMiddle(ListNode head) {
            ListNode slow = head;
            ListNode fast = head;

            while (fast.next != null && fast.next.next != null) {
                slow = slow.next;
                fast = fast.next.next;
            }

            ListNode middle = slow.next;
            slow.next = null; // 断开链表，分为两部分

            return middle;
        }

        private ListNode merge(ListNode l1, ListNode l2) {
            ListNode dummy = new ListNode(0);
            ListNode current = dummy;

            while (l1 != null && l2 != null) {
                if (l1.val < l2.val) {
                    current.next = l1;
                    l1 = l1.next;
                } else {
                    current.next = l2;
                    l2 = l2.next;
                }
                current = current.next;
            }

            // 将未处理完的部分连接到已排序部分的末尾
            if (l1 != null) {
                current.next = l1;
            } else {
                current.next = l2;
            }

            return dummy.next;
        }
    }

    /**
     * 2、自底向上的归并排序 -- 16ms(22.15%), 55.25MB(31.37%)
     * <p>
     * 时间复杂度：O(nlogn)
     * <p>
     * 空间复杂度：O(1)
     */
    public class Solution2 {
        public ListNode sortList(ListNode head) {
            // 如果链表为空或只有一个节点，无需排序，直接返回
            if (head == null || head.next == null) {
                return head;
            }

            // 获取链表的长度
            int length = getLength(head);
            // 创建一个虚拟节点作为辅助节点
            ListNode dummy = new ListNode(0, head);

            // 循环执行归并排序
            for (int size = 1; size < length; size *= 2) {
                ListNode prev = dummy;
                ListNode curr = dummy.next;

                // 分割、合并链表
                while (curr != null) {
                    ListNode left = curr;
                    ListNode right = split(left, size);
                    curr = split(right, size);

                    prev = merge(left, right, prev);
                }
            }

            return dummy.next;
        }

        // 获取链表的长度
        private int getLength(ListNode head) {
            int length = 0;
            while (head != null) {
                length++;
                head = head.next;
            }
            return length;
        }

        // 将链表分为两部分，返回第二部分的头节点
        private ListNode split(ListNode head, int size) {
            if (head == null) {
                return null;
            }

            for (int i = 1; i < size && head.next != null; i++) {
                head = head.next;
            }

            ListNode second = head.next;
            head.next = null;
            return second;
        }

        // 合并两个已排序的链表，并连接到前一个节点
        private ListNode merge(ListNode l1, ListNode l2, ListNode prev) {
            ListNode curr = prev;

            while (l1 != null && l2 != null) {
                if (l1.val < l2.val) {
                    curr.next = l1;
                    l1 = l1.next;
                } else {
                    curr.next = l2;
                    l2 = l2.next;
                }
                curr = curr.next;
            }

            // 将剩余的部分连接到已排序的链表
            curr.next = (l1 != null) ? l1 : l2;

            // 移动到已排序链表的末尾
            while (curr.next != null) {
                curr = curr.next;
            }

            return curr;
        }
    }

    /**
     * 3、快速排序 -- 超出时间限制
     * <p>
     * 时间复杂度：最坏 -- O(n^2), 平均 -- O(nlogn)
     * <p>
     * 空间复杂度：最坏 -- O(n), 平均 -- O(logn)
     */
    class Solution3 {
        public ListNode sortList(ListNode head) {
            return quickSortLinkedList(head)[0];
        }

        public ListNode[] quickSortLinkedList(ListNode head) {
            if (head == null || head.next == null) return new ListNode[]{head, head};
            // pivot为head，定义跟踪分割左右两个链表的头尾指针
            ListNode p = head.next, headSmall = new ListNode(), headBig = new ListNode(), tailSmall = headSmall, tailBig = headBig;

            // partition操作，以pivot为枢纽分割为两个链表
            while (p != null) {
                if (p.val < head.val) {
                    tailSmall.next = p;
                    tailSmall = tailSmall.next;
                } else {
                    tailBig.next = p;
                    tailBig = tailBig.next;
                }
                p = p.next;
            }

            // 断开<pivot的排序链表、pivot、>=pivot的排序链表，链表变为三个部分
            head.next = null;
            tailSmall.next = null;
            tailBig.next = null;

            // 递归partition
            ListNode[] left = quickSortLinkedList(headSmall.next);
            ListNode[] right = quickSortLinkedList(headBig.next);


            // 如果有<pivot的排序链表、连接pivot
            if (left[1] != null) {
                left[1].next = head;
            }
            // 连接pivot、>=pivot的排序链表
            head.next = right[0];

            // 确定排序后的头节点和尾节点
            ListNode newHead, newTail;
            if (left[0] != null) newHead = left[0];
            else newHead = head;
            if (right[1] != null) newTail = right[1];
            else newTail = head;

            // 返回当前层递归排序好的链表头节点和尾节点
            return new ListNode[]{newHead, newTail};
        }
    }

    /**
     * 4、动态数组 -- 12ms(45.77%), 53.89MB(75.78%)
     * <p>
     * 时间复杂度：O(nlogn)
     * <p>
     * 空间复杂度：O(n)
     */

     // 定义一个类 Solution4
     class Solution4 {
         // 定义一个方法，参数为 ListNode 类型的 head，返回值为 ListNode 类型的 head
         public ListNode sortList(ListNode head) {
             // 如果 head 为空，返回 null
             if (head == null) {
                 return null;
             }

             // 定义一个 ArrayList 类型的 list，用于存放 ListNode 类型的节点
             List<ListNode> list = new ArrayList<>();
             // 使用 while 循环，将 head 中的节点添加到 list 中
             while (head != null) {
                 list.add(head);
                 head = head.next;
             }
             // 对 list 中的节点进行排序，使用的是 Comparator 接口的 compare 方法
             list.sort(new Comparator<ListNode>() {
                 @Override
                 public int compare(ListNode o1, ListNode o2) {
                     // 返回 o1 和 o2 的 val 值之差
                     return o1.val - o2.val;
                 }
             });

             // 使用 for 循环，将 list 中的节点连接起来
             for (int i = 0; i < list.size() - 1; i++) {
                 list.get(i).next = list.get(i + 1);
             }
             // 设置 list 中最后一个节点的 next 为 null
             // 确保链表在遍历结束后能够正确地排序和连接，从而实现链表的排序和连接。
             list.get(list.size() - 1).next = null;
             // 返回 list 中第一个节点的指针
             return list.get(0);
         }
     }


}
