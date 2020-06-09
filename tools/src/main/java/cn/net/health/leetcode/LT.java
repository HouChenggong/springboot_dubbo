package cn.net.health.leetcode;

import org.openjdk.jol.info.ClassLayout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class LT {
    public static void main(String[] args) {
        int arr[] = new int[]{};
        String arrStr = ClassLayout.parseInstance(arr).toPrintable();
        System.out.println(arrStr);
    }

}
