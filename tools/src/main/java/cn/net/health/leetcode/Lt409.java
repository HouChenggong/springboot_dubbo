package cn.net.health.leetcode;

public class Lt409 {
    public int longestPalindrome(String s) {
        int len = s.length();
        if (len <= 1) {
            return len;
        }
        int arr[] = new int[52];

        for (int i = 0; i < len; i++) {
            int one = s.charAt(i) - 'a';
            arr[one] += 1;
        }
        int total = 0;
        int a = 0;
        for (int i = 0; i < 58; i++) {
            if (arr[i] % 2 == 0) {
                total += arr[i];
            } else {
                if (arr[i] / 2 > 1) {
                    total += arr[i] / 2;
                    a = 1;
                } else {
                    a = 1;
                }

            }
        }
        return total + a;

    }

    public static void main(String[] args) {
        System.out.println((int)'a');
        System.out.println((int)'z');
        System.out.println((int)'A');
        System.out.println((int)'Z');
        System.out.println((int)'0');
        System.out.println((int)'9');
    }
}
