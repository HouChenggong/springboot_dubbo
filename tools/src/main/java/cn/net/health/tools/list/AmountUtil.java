package cn.net.health.tools.list;

/**
 * @author xiyou
 * @version 1.2
 * @date 2020/1/9 18:09
 */

import java.math.BigDecimal;

/**
 * 金额计算工具
 *
 *
 * @author poll
 * @version $Id: AmountUtil.java, v 0.1 2016年11月9日 上午10:58:19 poll Exp $
 */
public class AmountUtil {

    /**
     * 由于Java的简单类型不能够精确的对浮点数进行运算，这个工具类提供精
     * 确的浮点数运算，包括加减乘除和四舍五入。
     *
     * 一：在 Java 中写入 new BigDecimal(0.1)
     * 但是它实际上等于 0.1000000000000000055511151231257827021181583404541015625。
     * 这是因为 0.1 无法准确地表示为 double（或者说对于该情况，不能表示为任何有限长度的二进制小数）。
     * 这样，传入 到构造方法的值不会正好等于 0.1（虽然表面上等于该值）。
     * 二：另一方面，String 构造方法是完全可预知的：写入 new BigDecimal("0.1") 将创建一个 BigDecimal，
     * 它正好 等于预期的 0.1。因此，比较而言，通常建议优先使用 String 构造方法。
     * 三：使用public static BigDecimal valueOf(double val)
     * 使用 Double.toString(double) 方法提供的 double 规范的字符串表示形式将 double 转换为 BigDecimal。
     * 这通常是将 double（或 float）转化为 BigDecimal 的首选方法，
     * 因为返回的值等于从构造 BigDecimal（使用 Double.toString(double) 得到的结果）得到的值。
     */

    //默认除法运算精度
    private static final int DEF_DIV_SCALE = 2;

    //这个类不能实例化

    private AmountUtil() {

    }

    public static BigDecimal toBigDecimal(double v) {
        return new BigDecimal(Double.valueOf(v));
    }

    public static double toDoubleValue(BigDecimal b) {
        if (b == null) {
            return 0.0;
        } else {
            return b.doubleValue();
        }
    }

    /**
     * 金额单位转为分
     *
     * @param amt
     * @return
     */
    public static long unitToCent(double v) {

        BigDecimal a = new BigDecimal(Double.toString(v));
        BigDecimal b = BigDecimal.valueOf(100);

        return a.multiply(b).longValue();
    }

    /**
     * 金额单位转为元
     *
     * @param amt
     * @return
     */
    public static double unitToYuan(long v) {

        BigDecimal b1 = new BigDecimal(Long.toString(v));

        BigDecimal b2 = new BigDecimal(Long.toString(100));

        return b1.divide(b2).doubleValue();
    }

    /** */
    /**

     * 提供精确的加法运算。

     * @param v1 被加数

     * @param v2 加数

     * @return 两个参数的和

     */
    public static double add(double v1, double v2) {

        BigDecimal b1 = new BigDecimal(Double.toString(v1));

        BigDecimal b2 = new BigDecimal(Double.toString(v2));

        return b1.add(b2).doubleValue();

    }

    /**

     * 提供精确的减法运算。

     * @param v1 被减数

     * @param v2 减数

     * @return 两个参数的差

     */
    public static double sub(double v1, double v2) {

        BigDecimal b1 = new BigDecimal(Double.toString(v1));

        BigDecimal b2 = new BigDecimal(Double.toString(v2));

        return b1.subtract(b2).doubleValue();

    }

    /**

     * 提供精确的乘法运算。

     * @param v1 被乘数

     * @param v2 乘数

     * @return 两个参数的积

     */
    public static double mul(double v1, double v2) {

        BigDecimal b1 = new BigDecimal(Double.toString(v1));

        BigDecimal b2 = new BigDecimal(Double.toString(v2));

        return b1.multiply(b2).doubleValue();

    }

    /** */
    /**

     * 提供（相对）精确的除法运算，当发生除不尽的情况时，精确到

     * 小数点以后2位，以后的数字四舍五入。

     * @param v1 被除数

     * @param v2 除数

     * @return 两个参数的商

     */
    public static double div(double v1, double v2) {

        return div(v1, v2, DEF_DIV_SCALE);

    }

    /**

     * 提供（相对）精确的除法运算。当发生除不尽的情况时，由scale参数指

     * 定精度，以后的数字四舍五入。

     * @param v1 被除数

     * @param v2 除数

     * @param scale 表示表示需要精确到小数点以后几位。

     * @return 两个参数的商

     */
    public static double div(double v1, double v2, int scale) {

        if (scale < 0) {

            throw new IllegalArgumentException("The scale must be a positive integer or zero");

        }

        BigDecimal b1 = new BigDecimal(Double.toString(v1));

        BigDecimal b2 = new BigDecimal(Double.toString(v2));

        return b1.divide(b2, scale, BigDecimal.ROUND_HALF_UP).doubleValue();

    }

    /** */
    /**

     * 提供精确的小数位四舍五入处理。

     * @param v 需要四舍五入的数字

     * @param scale 小数点后保留几位

     * @return 四舍五入后的结果

     */
    public static double round(double v, int scale) {

        if (scale < 0) {
            throw new IllegalArgumentException("The scale must be a positive integer or zero");

        }

        BigDecimal b = new BigDecimal(Double.toString(v));

        BigDecimal one = new BigDecimal("1");

        return b.divide(one, scale, BigDecimal.ROUND_HALF_UP).doubleValue();

    }

    /**
     * if a==b return true, else return false;
     * @param a
     * @param b
     * @return
     */
    public static boolean equal(double a, double b) {
        if ((a - b > -0.001) && (a - b) < 0.001) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * if a>＝b return true, else return false;
     * @param a
     * @param b
     * @return
     */
    public static boolean compare(double a, double b) {
        if (a - b > -0.001)
            return true;
        else
            return false;
    }

    /**
     * if a>b return true, else return false;
     * @param a
     * @param b
     * @return
     */
    public static boolean bigger(double a, double b) {
        if (a - b > 0.001)
            return true;
        else
            return false;
    }

    /**
     *
     * 返回不小于 value(保留scale位小数)的下一个数。
     * <br>12.1310->12.14
     * <br>12.13009->12.14
     * @param int scale 保留的小数位
     * @return double
     */
    public static double ceiling(double v, int scale) {

        if (scale < 0) {

            throw new IllegalArgumentException("The scale must be a positive integer or zero");

        }

        BigDecimal b = new BigDecimal(Double.toString(v));

        BigDecimal one = new BigDecimal("1");

        return b.divide(one, scale, BigDecimal.ROUND_UP).doubleValue();

    }

    /**
     *
     * 返回不大于 value(保留scale位小数)的下一个数。
     * @param v
     * @param scale
     * @return
     */
    public static double tailing(double v, int scale) {

        if (scale < 0) {
            throw new IllegalArgumentException("The scale must be a positive integer or zero");
        }

        BigDecimal b = new BigDecimal(Double.toString(v));

        BigDecimal one = new BigDecimal("1");

        return b.divide(one, scale, BigDecimal.ROUND_DOWN).doubleValue();

    }

    /**
     *
     * 对精度后一位，进一取值
     * <br>12.1310->12.14
     * <br>12.1309->12.13
     * @param v
     * @param scale
     * @return
     */
    public static double increaseOne(double v, int scale) {
        if (scale < 0) {
            throw new IllegalArgumentException("The scale must be a positive integer or zero");
        }

        BigDecimal b = new BigDecimal(Double.toString(v));

        return b.setScale(scale + 1, BigDecimal.ROUND_DOWN).setScale(scale, BigDecimal.ROUND_UP).doubleValue();
    }

}


