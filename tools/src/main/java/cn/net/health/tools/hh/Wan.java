package cn.net.health.tools.hh;

import java.util.Arrays;
import java.util.Comparator;
import java.util.stream.Collectors;

public class Wan {


    public static void main(String[] args) {
        System.out.println(getResult());
    }

    /**
     * 判断是否是数字
     *
     * @param str
     * @return
     */
    public static boolean isNum(String str) {
        for (int i = 0; i < str.length(); i++) {
            if (Character.isDigit(str.charAt(i))) {
                return true;
            }
        }
        return false;
    }

    /**
     * 我想找出所有 长度>=5的字符串，
     * 并且忽略大小写、去除重复字符串，然后按字母排序，
     * 最后用“爱心❤”连接成一个字符串输出！
     *
     * @return
     */
    private static String getResult() {
        String arr[] = new String[]{
                "1 ", "2 ", "bilibili ", "of ", "codesheep ", "5 ", "at ",
                "BILIBILI ", "codesheep ", "23 ", "CHEERS ", "6 "};

        String result = Arrays.stream(arr)
                //数字过滤
                .filter(i -> !isNum(i))
                //过滤长度大于5的
                .filter(one -> one.length() > 5)
                //字符串全部小写
                .map(one -> one.toLowerCase())
                //去重
                .distinct()
                //自然语言排序
                .sorted(Comparator.naturalOrder())
                //连接字符串
                .collect(Collectors.joining(("❤")));
        return result;
    }
}
