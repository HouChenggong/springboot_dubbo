package cn.net.health.tools.digui;

/**
 * @author xiyou
 * @version 1.2
 * @date 2020/1/18 9:12
 * 细胞分离
 * 细胞分裂 有一个细胞 每一个小时分裂一次，一次分裂一个子细胞，第三个小时后会死亡。那么n个小时候有多少细胞？
 */
public class Cell {


    /**
     * 这是我刚开始想到的答案，怎么解释呢：就是细胞在0，1，2，3小时的时候分别是0，1，2，4
     * 在第4个小时要死一个，f（n）=2f(n-1)-f(n-3)=4*2-1=7
     * f(5)=2f(n-1)-（f(n-3)-f(n-4))==7*2-(2-1)=13因为第5个小时要死掉2个，但是前面已经死掉了一个，所以要减去前面的一个
     * f(6)=2f(5)-(f(3)-f(3))=13*2-(4-2)=24
     * f(7)=2f(6)-((f(4)-f(3))=24*2-(7-4)=45
     * 所以我不加思索的写下来了下面的递归方案
     * n从1到10结果如下：1 2 4 7 13 24 45 84 157 293
     * <p>
     * <p>
     * * 但是问题出现了，如果我们仔细观察图的话，我们就发现其实第7个小时死掉的不是（7-4=3）个，而是4个，我们看图就能看出来
     * https://github.com/HouChenggong/springboot_dubbo/blob/master/tools/src/main/resources/cell.jpg
     * 图片地址：https://github.com/HouChenggong/springboot_dubbo/blob/master/tools/src/main/resources/cell.jpg
     *
     * @param n
     * @return
     */
    public static int getAllCells(int n) {
        if (n == 0) {
            return 0;
        } else if (n == 1) {
            return 1;
        } else if (n == 2) {
            return 2;
        } else if (n == 3) {
            return 4;
        } else {
            return 2 * getAllCells(n - 1) - (getAllCells(n - 3) - getAllCells(n - 4));
        }
    }


    /**
     * 换个方式，其实N个小时之后的细胞=分离1次的细胞个数+分裂2次的细胞个数+分离3次的细胞个数
     *
     * @param n
     * @return
     */
    public int allCells(int n) {
        return aCell(n) + bCell(n) + cCell(n);
    }

    /**
     * 第 n 小时 a 状态的细胞数
     */
    public int aCell(int n) {
        if (n == 1) {
            return 1;
        } else {
            return aCell(n - 1) + bCell(n - 1) + cCell(n - 1);
        }
    }

    /**
     * 第 n 小时 b 状态的细胞数
     */
    public int bCell(int n) {
        if (n == 1) {
            return 0;
        } else {
            return aCell(n - 1);
        }
    }

    /**
     * 第 n 小时 c 状态的细胞数
     */
    public int cCell(int n) {
        if (n == 1 || n == 2) {
            return 0;
        } else {
            return bCell(n - 1);
        }
    }


    public static void main(String[] args) {

        for (int i = 1; i < 11; i++) {
            Cell cell = new Cell();

            System.out.println(cell.allCells(i));
            System.out.println(i + "----------" + getAllCells(i));
        }
    }
}
