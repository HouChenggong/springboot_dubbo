package cn.net.health.tools.hash;

/**
 * @author xiyou
 * @version 1.2
 * @date 2020/1/3 9:37
 */
public class TestConcurrentHashMap {
    public static void main(String[] args) {
        System.out.println("十进制值：" + Integer.parseInt("0001111", 2));
        System.out.println(Integer.parseInt("0001111", 2) & 15);
        System.out.println("十进制值：" + Integer.parseInt("0011111", 2));
        System.out.println(Integer.parseInt("0011111", 2) & 15);
        System.out.println("十进制值：" + Integer.parseInt("0111111", 2));
        System.out.println(Integer.parseInt("0111111", 2) & 15);
        System.out.println("十进制值：" + Integer.parseInt("1111111", 2));
        System.out.println(Integer.parseInt("1111111", 2) & 15);
    }
}
