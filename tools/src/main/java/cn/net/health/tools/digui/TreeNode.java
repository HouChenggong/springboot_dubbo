package cn.net.health.tools.digui;

import lombok.Data;

/**
 * @author xiyou
 * @version 1.2
 * @date 2020/1/16 19:45
 */
@Data
public class TreeNode {
    public int value;
    public TreeNode left;
    public TreeNode right;

    public TreeNode(int key) {
        key = value;
    }





}
