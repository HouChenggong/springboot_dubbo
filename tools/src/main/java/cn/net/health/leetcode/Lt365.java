package cn.net.health.leetcode;

/**
 * @author xiyou
 */
public class Lt365 {

    public boolean canMeasureWater(int x, int y, int z) {
        if (x <= 0 || y <= 0 || z <= 0) {
            return false;
        }
        if (x == 1 || y == 1) {
            return true;
        }
        if (z % x == 0 || z % y == 0) {
            return true;
        }
        if (x == y && z % x != 0) {
            return false;
        }
        return z % gcd(x, y) == 0;

    }

    public static int gcd(int x, int y) {

        if (x < y) {
            int c = x;
            x = y;
            y = c;
        }
            while (y != 0) {
                if (x < y) {
                    int d = x;
                    x = y;
                    y = d;
                }
                int t =y;
                y = x % t;
                x = t;

            }


        return x;
    }

    public static void main(String[] args) {
        System.out.println(gcd(3, 5));
        System.out.println(gcd(15, 40));
    }
}
