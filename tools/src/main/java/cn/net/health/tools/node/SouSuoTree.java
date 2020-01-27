package cn.net.health.tools.node;

import cn.net.health.tools.digui.TreeNode;

import java.util.LinkedList;

/**
 * @author 希尤丨彦
 * 力扣938 二叉搜索树范围和
 * https://leetcode-cn.com/problems/range-sum-of-bst/
 */
public class SouSuoTree {


    /**
     * 用linkedList解题
     *
     * @param root
     * @param L
     * @param R
     * @return
     */
    public static int rangeSumBST(TreeNode root, int L, int R) {
        if (root == null) {
            return 0;
        }
        int res = 0;
        LinkedList<TreeNode> linkedList = new LinkedList<>();
        linkedList.addFirst(root);
        while (!linkedList.isEmpty()) {
            TreeNode one = linkedList.pollLast();
            if (one != null) {
                int value = one.value;
                if (value <= R && value >= L) {
                    res += value;
                    linkedList.addFirst(one.right);
                    linkedList.addFirst(one.left);
                } else if (value < L) {
                    linkedList.addFirst(one.right);
                } else if (value > R) {
                    linkedList.addFirst(one.left);
                }
            }
        }
        return res;

    }


    int total = 0;

    /**
     * 用递归的方案解题
     *
     * @param root
     * @param L
     * @param R
     * @return
     */
    public int rangeSumBST2(TreeNode root, int L, int R) {
        df(root, L, R);
        return total;
    }

    public void df(TreeNode root, int L, int R) {
        if (root != null) {
            int value = root.value;
            if (value <= R && value >= L) {
                total += value;
            }
            if (value > L) {
                df(root.left, L, R);
            }
            if (value < R) {
                df(root.right, L, R);
            }

        }
    }


    public static void main(String[] args) {
        TreeNode left11 = new TreeNode(3);
        TreeNode left12 = new TreeNode(7);


        TreeNode left1 = new TreeNode(5);
        left1.left = left11;
        left1.right = left12;


        TreeNode right12 = new TreeNode(18);

        TreeNode right1 = new TreeNode(15);
        right1.right = right12;
        TreeNode root = new TreeNode(10);
        root.left = left1;
        root.right = right1;


        System.out.println(root);
        System.out.println(
                rangeSumBST(root, 7, 15)
        );
    }
}
