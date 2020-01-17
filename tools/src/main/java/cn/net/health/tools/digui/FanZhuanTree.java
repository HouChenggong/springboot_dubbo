package cn.net.health.tools.digui;


import cn.net.health.tools.node.ListNode;

import java.util.LinkedList;

/**
 * @author xiyou
 * @version 1.2
 * @date 2020/1/16 19:44
 * https://leetcode-cn.com/problems/invert-binary-tree/submissions/
 */
public class FanZhuanTree {

    public static int count = 0;

    /**
     * 汉诺塔问题
     *
     * @param n
     * @param from
     * @param temp
     * @param to
     */
    public static void hnts(int n, String from, String temp, String to) {

        if (n == 1) {
            count++;
            System.out.println(from + "------>" + to + "........" + count);
        } else {
            hnts(n - 1, from, to, temp);
            hnts(1, from, temp, to);
            hnts(n - 1, temp, from, to);
        }

    }

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

    public TreeNode invertTree2(TreeNode root) {
        if (root == null) return null;
        LinkedList<TreeNode> queue = new LinkedList<TreeNode>();
        queue.add(root);
        while (!queue.isEmpty()) {
            TreeNode current = queue.poll();
            TreeNode temp = current.left;
            current.left = current.right;
            current.right = temp;
            if (current.left != null) queue.add(current.left);
            if (current.right != null) queue.add(current.right);
        }
        return root;
    }

    public static void main(String[] args) {

        Integer[] arr = new Integer[]{3, 9, 20, null, null, 15, 7};

        TreeNode treeNode = new TreeNode(0);
        treeNode.left = new TreeNode(1);
        treeNode.right = new TreeNode(2);
        treeNode = invertTree(treeNode);
        System.out.println(treeNode.left.value);


        hnts(5, "a", "b", "c");
        System.out.println("--------------------");
    }
}
