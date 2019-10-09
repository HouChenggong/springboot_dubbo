package cn.net.health.tools.time;

public class AddTwoNumbers {

    public static  ListNode addTwoNumbers(ListNode l1, ListNode l2) {


        if (l1 == null && l2 == null) {
            return null;
        } else if (l1 == null) {
            return l2;
        } else if (l2 == null) {
            return l1;
        } else {
            ListNode result = new ListNode(0);
            ListNode target = result;
            int isJinWei = 0;
            int x = 0;
            int y = 0;
            while (l1 != null || l2 != null) {
                if (l1 == null) {
                    x = 0;
                } else {
                    x = l1.val;
                }
                if (l2 == null) {
                    y = 0;
                } else {
                    y = l2.val;
                }
                int newNode = x + y + isJinWei;
                if (newNode >= 10) {
                    newNode = newNode - 10;
                }
                target.next = new ListNode(newNode);
                if (x + y + isJinWei >= 10) {
                    isJinWei = 1;
                } else {
                    isJinWei = 0;
                }
                if (l1 != null) {
                    l1 = l1.next;
                }
                if (l2 != null) {
                    l2 = l2.next;
                }
                target = target.next;

            }
            if (isJinWei == 1) {
                target.next = new ListNode(1);
            }

            return result.next;
        }

    }


    public static void main(String[] args) {
        ListNode root =new ListNode(2);
        ListNode root1 =new ListNode(3);
        ListNode root2 =new ListNode(4);


        root1.next=root2;
        root.next =root1;
        ListNode rootA =new ListNode(2);
        ListNode rootA1 =new ListNode(3);
        ListNode rootA2 =new ListNode(4);

        rootA1.next=root2;
        rootA.next =root1;

        ListNode result =addTwoNumbers(root,rootA);
        System.out.println(result.sout(result));

    }

}
