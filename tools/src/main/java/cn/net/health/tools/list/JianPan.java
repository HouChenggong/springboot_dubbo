package cn.net.health.tools.list;

import java.util.LinkedList;
import java.util.List;

/**
 * @author xiyou
 * @version 1.2
 * @date 2019/12/24 9:22
 */
public class JianPan {


    /**
     * 其实就是一个队列的问题
     * 力扣17题：电话号码的字母组合
     * https://leetcode-cn.com/problems/letter-combinations-of-a-phone-number/
     * 效率大概是：击败95%
     *
     * @param digits
     * @return
     */
    public static List<String> letterCombinations(String digits) {
        LinkedList<String> linkedList = new LinkedList<String>();
        if (digits.isEmpty()) {
            return linkedList;
        }
        String[] mapping = new String[]{"0", "1", "abc", "def", "ghi", "jkl", "mno", "pqrs", "tuv", "wxyz"};
        linkedList.add("");
        String peekOne = "";
        int len = digits.length();
        for (int i = 0; i < len; i++) {
            while (linkedList.peek().length() == i) {
                peekOne = linkedList.pop();
                int thisNum = Character.getNumericValue(digits.charAt(i));
                for (int j = 0; j < mapping[thisNum].length(); j++) {
                    linkedList.add(peekOne + mapping[thisNum].charAt(j));
                }

            }
        }

        return linkedList;
    }

    public static void main(String[] args) {
        System.out.println("1230".charAt(0));
        String digits = "123";
        char one = digits.charAt(0);
        int thisNum = Character.getNumericValue(one);
        System.out.println(thisNum);
        System.out.println(letterCombinations("234"));
    }
}
