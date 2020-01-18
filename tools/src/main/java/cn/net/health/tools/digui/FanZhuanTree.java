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


    /**
     * 汉诺塔问题，递归解法
     *
     * @param n
     * @param from
     * @param temp
     * @param to
     */
    public static void getCont(int n, String from, String temp, String to) {
        if (n <= 1) {
            System.out.println("from...." + from + ".......to...." + to);
            return;
        }
        getCont(n - 1, from, to, temp);
        getCont(1, from, temp, to);
        getCont(n - 1, temp, from, to);


    }

    public static void main(String[] args) {

        Integer[] arr = new Integer[]{3, 9, 20, null, null, 15, 7};
        getCont(1, "A", "B", "C");
        System.out.println("----------------");
        getCont(2, "A", "B", "C");
        System.out.println("----------------");
        getCont(3, "A", "B", "C");

    }
}
