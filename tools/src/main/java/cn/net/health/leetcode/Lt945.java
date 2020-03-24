package cn.net.health.leetcode;

public class Lt945 {

        public static int minIncrementForUnique(int[] A) {
            int len =A.length;
            if(len<=1){
                return 0;
            }
            if(len==2 && A[0]==A[1]){
                return 1;
            }
            if(len==2 && A[0]!=A[1]){
                return 0;
            }
            // counter数组统计每个数字的个数。
            //（这里为了防止下面遍历counter的时候每次都走到40000，所以设置了一个max，这个数据量不设也行，再额外设置min也行）
            int[] counter = new int[40001];
            int max = -1;
            for (int num: A) {
                counter[num]++;
                max = Math.max(max, num);
            }

            // 遍历counter数组，若当前数字的个数cnt大于1个，则只留下1个，其他的cnt-1个后移
            int move = 0;
            for (int num = 0; num <= max; num++) {
                if (counter[num] > 1) {
                    int d = counter[num] - 1;
                    move += d;
                    counter[num + 1] += d;
                }
            }
            // 最后, counter[max+1]里可能会有从counter[max]后移过来的，counter[max+1]里只留下1个，其它的d个后移。
            // 设 max+1 = x，那么后面的d个数就是[x+1,x+2,x+3,...,x+d],
            // 因此操作次数是[1,2,3,...,d],用求和公式求和。
            int d = counter[max + 1] - 1;
            move += (1 + d) * d / 2;
            return move;
        }

    public static void main(String[] args) {
            int arr []=new int[]{1,1,3,4,5,5,7,7,7,7};
        System.out.println(minIncrementForUnique(arr));
        System.out.println(Integer.parseInt("111"));
        System.out.println(Integer.parseInt("111a"));
        System.out.println(Integer.parseInt("a"));
    }


}
