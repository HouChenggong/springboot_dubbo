package cn.net.health.tools.lock;


public class A {
    public static final int num = 10;


    public int getTotal() {
        int a = 1;
        int b = 2;
        int c = (a + b) * 10;
        new Thread().start();
        return c;


    }
    public static int maxProfit3(int[] prices) {
        int len = prices.length;
        if (len <= 1) {
            return 0;
        }
        int low = prices[0];
        int max = 0;
        for (int i = 1; i < len; i++) {
            if (prices[i] > low) {
                max = Math.max(max, prices[i] - low);
            } else {
                low = prices[i];
            }
        }


 
        return max;

    }
    public static int maxProfit(int[] prices) {
        int len = prices.length;
        if (len <= 1) {
            return 0;
        }
        int low = prices[0];
        int max = 0;
        for (int i = 1; i < len; i++) {
            if (prices[i] > low) {
                max = Math.max(max, prices[i] - low);
            } else {
                low = prices[i];
            }
        }
        return max;

    }
    public static  int maxProfit2(int[] prices) {
        int len = prices.length;
        if (len <= 1) {
            return 0;
        }

        int low=0;
        int max = 0;
        for (int i = 1; i < len; i++) {
            if (prices[i] > prices[i-1]) {
                max +=prices[i]-prices[i-1];
            } else {
                low = prices[i];
            }
        }
        return max;
    }

    public static void main(String[] args) {
        A a = new A();
        int total = a.getTotal();
        System.out.println(total);

    }
}
