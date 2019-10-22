package cn.net.health.tools.str;

import java.util.ArrayList;
import java.util.List;

public class ZbianHuan {
    /**
     * https://leetcode-cn.com/problems/zigzag-conversion/
     */

    /**
     * 发现其实每一行的数字对应其下一行的数字都是N+(N-2)
     * 当遍历一遍的时候，如果是0，继续，行数+1
     * 如果当前行是N-1，说明遍历到了底部，要把行数-1
     * <p>
     * <p>
     * 执行用时 :10 ms, 在所有 java 提交中击败了79.35%的用户
     * 内存消耗 :37.3 MB, 在所有 java 提交中击败了98.99%的用户
     *
     * @param s
     * @param numRows
     * @return
     */
    public static String convert(String s, int numRows) {

        if (numRows == 1) return s;

        List<StringBuilder> rows = new ArrayList<>();
        for (int i = 0; i < Math.min(numRows, s.length()); i++)
            rows.add(new StringBuilder());

        int curRow = 0;
        boolean goingDown = false;

        for (char c : s.toCharArray()) {
            rows.get(curRow).append(c);
            if (curRow == 0) {
                goingDown = true;
            } else if (curRow == numRows - 1) {
                goingDown = false;
            } else {

            }
            if (goingDown) {
                curRow += 1;
            } else {
                curRow += -1;
            }

        }

        StringBuilder ret = new StringBuilder();
        for (StringBuilder row : rows) ret.append(row);
        return ret.toString();
    }

    public static void main(String[] args) {
        System.out.println(convert("LEETCODEISHIRING", 3));
    }

}
