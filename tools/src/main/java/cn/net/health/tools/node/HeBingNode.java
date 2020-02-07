package cn.net.health.tools.node;

/**
 * 力扣合并两个有序链表
 * https://leetcode-cn.com/problems/merge-two-sorted-lists/
 */
public class HeBingNode {
    /**
     * 循环解法
     *
     * @param l1
     * @param l2
     * @return
     */
    public ListNode mergeTwoLists(ListNode l1, ListNode l2) {
        if (l1 == null) return l2;
        if (l2 == null) return l1;
        ListNode result = new ListNode(-1);
        ListNode root = result;

        while (l1 != null && l2 != null) {
            if (l1.val < l2.val) {
                root.next = new ListNode(l1.val);
                l1 = l1.next;
            } else {
                root.next = new ListNode(l2.val);
                l2 = l2.next;
            }
            root = root.next;
        }
        if (l1 == null) {
            root.next = l2;
        }
        if (l2 == null) {
            root.next = l1;
        }
        return result.next;


    }

    /**
     * 递归解法
     *
     * @param l1
     * @param l2
     * @return
     */
    public ListNode mergeTwoLists2(ListNode l1, ListNode l2) {
        if (l1 == null) return l2;
        if (l2 == null) return l1;
        if (l1.val < l2.val) {
            l1.next = mergeTwoLists2(l1.next, l2);
            return l1;
        } else {
            l2.next = mergeTwoLists2(l1, l2.next);
            return l2;
        }

    }
}
