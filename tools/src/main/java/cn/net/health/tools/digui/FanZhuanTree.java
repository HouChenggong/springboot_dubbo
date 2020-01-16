package cn.net.health.tools.digui;


import cn.net.health.tools.node.ListNode;

/**
 * @author xiyou
 * @version 1.2
 * @date 2020/1/16 19:44
 */
public class FanZhuanTree {

    public static TreeNode invertTree(TreeNode root) {
        if (root == null) {
            return null;
        }
        TreeNode left = invertTree(root.left);
        TreeNode right = invertTree(root.right);
        root.left = right;
        root.right = left;
        return root;
    }

    public static void main(String[] args) {

        Integer[] arr = new Integer[]{3, 9, 20, null, null, 15, 7};


    }
}
