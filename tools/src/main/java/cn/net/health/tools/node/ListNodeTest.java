package cn.net.health.tools.node;


/**
 * @author xiyou
 * @version 1.2
 * @date 2019/12/25 19:53
 * 题目： 删除链表倒数第N个节点
 * https://leetcode-cn.com/problems/remove-nth-node-from-end-of-list/
 */
public class ListNodeTest {

    /**
     * 普通方法：先遍历一遍找到长度
     * 然后再移除
     *
     * @param head
     * @param n
     * @return
     */
    public static ListNode removeNthFromEnd(ListNode head, int n) {
        ListNode dummy = new ListNode(0);
        dummy.next = head;
        int length = 0;
        ListNode first = head;
        while (first != null) {
            length++;
            first = first.next;
        }
        System.out.println(length);
        length -= n;
        first = dummy;
        while (length > 0) {
            length--;
            first = first.next;
        }
        first.next = first.next.next;
        return dummy.next;
    }

    /**
     * 双指针操作，第一个节点先跑N个位置，然后两个节点一起跑，第一个节点因为先跑，肯定要先到
     * 第一个节点到了，其实移除第二个节点的下一个节点就可
     *
     * @param head
     * @param n
     * @return
     */
    public ListNode removeNthFromEndTwo(ListNode head, int n) {
        ListNode dummy = new ListNode(0);
        dummy.next = head;
        ListNode first = dummy;
        ListNode second = dummy;
        for (int i = 0; i <= n; i++) {
            first = first.next;
        }
        while (first != null) {
            first = first.next;
            second = second.next;
        }
        second.next = second.next.next;
        return dummy.next;
    }

    public static void main(String[] args) {
        ListNode root = new ListNode(1);
        ListNode root1 = new ListNode(2);
        ListNode root2 = new ListNode(3);
        ListNode root4 = new ListNode(4);
        ListNode root5 = new ListNode(5);


        root4.next = root5;
        root2.next = root4;
        root1.next = root2;
        root.next = root1;
        System.out.println(removeNthFromEnd(root, 2));
    }
}
