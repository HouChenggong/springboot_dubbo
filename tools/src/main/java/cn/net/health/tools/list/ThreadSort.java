package cn.net.health.tools.list;

/**
 * @author xiyou
 * @version 1.2
 * @date 2019/12/19 9:34
 */
public class ThreadSort {

    /**
     * 睡眠排序
     *
     * @param array
     */
    public static void sleepSort(int[] array) {
        for (int num : array) {
            new Thread(() -> {
                try {
                    Thread.sleep(num);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(num);
            }).start();
        }
    }

    /**
     * 珠排序算法，但是有一个最大的问题就是：数组必须连续而且不能为0和负数
     * 而且不能处理重复数据
     *
     * @param array
     * @return
     */
    public static int[] zhuSort(int[] array) {
        int len = array.length;
        int result[] = new int[len];
        for (int i = 0; i < len; i++) {
            for (int j = 0; j < array[i]; j++) {
                result[j] = result[j] + 1;
            }
        }
        return result;
    }

    /**
     * 珠排序算法完全版
     *
     * @param array
     * @return
     */
    public static int[] zhuSort2(int[] array) {
        int len = array.length;
        int max = 0;
        //求出数组里面的最大值，其实也是珠最大的值
        for (int one : array) {
            max = Math.max(one, max);
        }
        //result[i]是落下后珠的每一列珠的高度
        int result[] = new int[max];
        for (int i = 0; i < len; i++) {
            for (int j = 0; j < array[i]; j++) {
                result[j] = result[j] + 1;
            }
        }
        StringBuffer result3Str = new StringBuffer();
        for (int i : result) {
            result3Str.append(i + ",");
        }
        //result3Str就是众多珠落下后组成的字符串数组
        System.out.println(result3Str.toString());

        int num = 0;
        int res[] = new int[len];
        //当前横着的长度与下一个横着的相减的值
        int high = 0;
        for (int i = 0; i < max - 1; i++) {
            high = result[i] - result[i + 1];
            if (high > 0) {
                res[num] = i + 1;
                num++;
                while (high > 1) {
                    res[num] = res[num - 1];
                    high--;
                    num++;
                }
            }


        }
        res[len - 1] = max;
        return res;
    }

    public static void main(String[] args) {
        int arr3[] = new int[]{6, 2, 4, 1, 2, 5, 9, 6, 6, 7, 23, 34, 54, 2};
//        sleepSort(arr);
        int result3[] = zhuSort2(arr3);
        StringBuffer result3Str = new StringBuffer();
        for (int i : result3) {
            result3Str.append(i + ",");
        }
        System.out.println(result3Str.toString());
    }

}
