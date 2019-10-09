package cn.net.health.tools.time;

public class ListNode {
    int val;
    ListNode next;

    ListNode(int x) {
        val = x;
    }


    public String sout(ListNode listNode){
        ListNode newNode =listNode;
        StringBuffer stringBuffer =new StringBuffer("");
        while (newNode!=null){
            stringBuffer.append(newNode.val+",");
            newNode=newNode.next;
        }
        return stringBuffer.toString();
    }
}

