package cn.net.health.tools.tree;

import cn.net.health.tools.digui.TreeNode;

import java.util.ArrayList;
import java.util.List;

/**
 * @author xiyou
 * 对树进行前、中、后遍历
 */
public class TreeForEach {
    /**
     * 生成二叉树
     *
     * @param array
     * @param index
     * @return
     */
    private static TreeNode createBinaryTreeByArray(Integer[] array, int index) {
        TreeNode tn = null;
        if (index < array.length) {
            Integer value = array[index];
            if (value == null) {
                return null;
            }
            tn = new TreeNode(value);
            tn.left = createBinaryTreeByArray(array, 2 * index + 1);
            tn.right = createBinaryTreeByArray(array, 2 * index + 2);
            return tn;
        }
        return tn;
    }


    public List<Integer> preorderTraversal(TreeNode root) {
        List<Integer> list = new ArrayList<>(10);
        dfs(root, list);
        return list;
    }

    public void dfs(TreeNode root, List<Integer> list) {
        if (root == null) {
            return;
        }
        System.out.println(root.value);
        list.add(root.value);
        dfs(root.left, list);
        dfs(root.right, list);
    }


    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        if (root == null) {
            return null;
        }
        System.out.println(root.value+"   __");
        if (root.value == p.value || root.value == q.value) {
            return root;
        }
        TreeNode l = lowestCommonAncestor(root.left, p, q);
        TreeNode r = lowestCommonAncestor(root.right, p, q);
        if (l != null && r != null) {
            return root;
        }
        return l == null ? r : l;
    }

    public static void main(String[] args) {
        Integer[] arr = new Integer[]{3, 5, 1, 6, 2, 0, 8, null, null, 7, 4};
        TreeNode root = createBinaryTreeByArray(arr, 0);
        TreeForEach forEach = new TreeForEach();
        forEach.preorderTraversal(root);
        System.out.println("上面是前序遍历的结果");
       root=  forEach.lowestCommonAncestor(root, new TreeNode(8,null,null), new TreeNode(7,null,null));
        System.out.println(root.value+"这时最终结果");
    }
}
