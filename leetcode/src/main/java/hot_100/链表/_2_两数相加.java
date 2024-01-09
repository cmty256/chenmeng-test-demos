package hot_100.链表;


import hot_100.ListNode;

/**
 * 两数相加 -- 递归、链表、数学
 *
 * @author 沉梦听雨
 **/
@SuppressWarnings("all")
public class _2_两数相加 {

    /* 给你两个 非空 的链表，表示两个非负的整数。
       它们每位数字都是按照 逆序 的方式存储的，并且每个节点只能存储 一位 数字。
       请你将两个数相加，并以相同形式返回一个表示和的链表。
       你可以假设除了数字 0 之外，这两个数都不会以 0 开头。 */

    /**
     * 1、简约版 -- 模拟, 创建虚拟节点 -- 1ms(100%), 42.50MB(8.59%)
     *
     * 输入：l1 = [2,4,3], l2 = [5,6,4]
     * 输出：[7,0,8]
     *
     * 解释：342 + 465 = 807.
     */
    class Solution1 {
        public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
            // 创建一个虚拟节点
            ListNode dummy = new ListNode(0);
            // 进位
            int carry = 0;
            // 当前节点
            ListNode cur = dummy;
            // 当l1或l2不为空或者进位不为0时，循环
            while (l1 != null || l2 != null || carry != 0) {
                // 1、计算两个节点相加的值
                int s = (l1 == null ? 0 : l1.val) + (l2 == null ? 0 : l2.val) + carry;
                // 2、计算进位
                carry = s / 10;
                // 3、将计算的值赋值给当前节点
                cur.next = new ListNode(s % 10);
                // 4、将当前节点指向下一个节点
                cur = cur.next;
                // 5、将l1和l2指向下一个节点
                l1 = l1 == null ? null : l1.next;
                l2 = l2 == null ? null : l2.next;
            }
            // 返回虚拟节点的下一个节点
            return dummy.next;
        }
    }

    /**
     *  2、详细版
     *
     * 输入：l1 = [2,4,3], l2 = [5,6,4]
     * 输出：[7,0,8]
     *
     * 解释：342 + 465 = 807.
     */
    class Solution2 {
        public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
            // 创建一个新的链表pre，并将其头结点赋值为0，用作答案链表的头结点
            ListNode pre = new ListNode(0);
            // 创建一个指针cur，指向pre的最后一个节点
            ListNode cur = pre;
            // 进位数
            int carry = 0;

            while(l1 != null || l2 != null) {
                int x = l1 == null ? 0 : l1.val;//3 4 2
                int y = l2 == null ? 0 : l2.val;//4 6 5
                int sum = x + y + carry;//7 10 8
                // 更新进位
                carry = sum / 10;//0 1 0
                // 当前两位节点的和
                sum = sum % 10;//7 0 8
                // 将sum对10取余得到当前位节点的值，用它创建一个新的节点，并将其作为答案链表的下一个节点
                cur.next = new ListNode(sum);//7 0 8

                // 将指针cur指向新创建的节点，以便后续使用cur指针来连接下一个新节点
                cur = cur.next;//7 0 8
                if(l1 != null)
                    l1 = l1.next;//4 2
                if(l2 != null)
                    l2 = l2.next;//6 5
            }
            // 链表遍历结束后，有carry > 0，还需要在答案链表的后面附加一个节点
            if(carry == 1) {
                cur.next = new ListNode(carry);
            }
            return pre.next;
        }
    }

}
