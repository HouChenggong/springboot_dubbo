package cn.net.health.tools.tree;

import org.w3c.dom.Node;

import java.util.PriorityQueue;
import java.util.Queue;

/**
 * @author xiyou
 * 漫画解读什么是哈曼西树
 * https://mp.weixin.qq.com/s/dX-8zmSemS0jU21esEYhZA
 */
public class HuffmanTree {

    private HmNode root;

    private HmNode[] list;


    public static void main(String[] args) {
        int arr[] = new int[]{2, 3, 7, 9, 18, 25};
        HuffmanTree tree = new HuffmanTree();
        tree.createHuffmanTree(arr);
        tree.outPrint(tree.root);
    }

    public void createHuffmanTree(int[] weight) {
        Queue<HmNode> nodeQueue = new PriorityQueue<>(weight.length);
        list = new HmNode[weight.length];

        /**
         * 初始化优先队列和原始node
         */
        for (int i = 0, len = weight.length; i < len; i++) {
            list[i] = new HmNode(weight[i]);
            nodeQueue.add(list[i]);
        }
        //主循环，当结点队列只剩一个结点时结束
        while (nodeQueue.size() > 1) {
            HmNode left = nodeQueue.poll();
            HmNode right = nodeQueue.poll();
            //parent
            HmNode parent = new HmNode(left.weight + right.weight, left, right);
            nodeQueue.add(parent);
        }
        root = nodeQueue.poll();
    }


    //前序遍历结果输出
    public void outPrint(HmNode node) {
        if (node == null) {
            return;
        }
        System.out.println(node.weight);
        outPrint(node.left);
        outPrint(node.right);

    }

    /*
        构建树,因为使用的是优先队列，所以用实现比较接口
     */
    public static class HmNode implements Comparable<HmNode> {
        int weight;
        HmNode left;
        HmNode right;

        public HmNode(int weight, HmNode left, HmNode right) {
            this.weight = weight;
            this.left = left;
            this.right = right;
        }

        public HmNode(int weight) {
            this.weight = weight;
        }

        @Override
        public int compareTo(HmNode node) {
            return Integer.valueOf(this.weight).compareTo(Integer.valueOf(node.weight));
        }
    }

}
