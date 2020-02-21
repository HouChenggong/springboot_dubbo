package cn.net.health.tools;

/**
 * @author xiyou
 */
public class CatchTest {

    /**
     * 结果是try2
     * 理解：在try语句的return块中，return 返回的引用变量（t 是引用类型）并不是try语句外定义的引用变量t，
     * 而是系统重新定义了一个局部引用t’，这个引用指向了引用t对应的值，
     * 即使在finally语句中把引用t指向了值finally，因为return的返回引用已经不是t ，所以引用t的对应的值和try语句中的返回值无关了。
     *
     * @return
     */
    public static String try1() {
        String t = "1";
        System.out.println("刚开始是：" + t);
        try {
            t = "try2";
            System.out.println("执行try方法结果：" + t);
            return t;
        } catch (Exception e) {
            e.printStackTrace();
            t = "catch3";
            System.out.println("执行catch方法结果：" + t);
            return t;
        } finally {
            t = "finally3";
            System.out.println("执行finally方法" + t);
        }

    }

    /**
     * 结果是finally3
     * try 和finally都有return 返回finally中的
     *
     * @return
     */
    public static String try2() {
        String t = "1";
        System.out.println("刚开始是：" + t);
        try {
            t = "try2";
            System.out.println("执行try方法结果：" + t);
            return t;
        } catch (Exception e) {
            e.printStackTrace();
            t = "catch3";
            System.out.println("执行catch方法结果：" + t);
            return t;
        } finally {
            t = "finally3";
            System.out.println("执行finally方法" + t);
            return t;
        }

    }


    /**
     * catch
     * 和方法try1一样，我们认为try catch 是一个方法，方法内部要返回的值是方法内部的临时变量与外部真实的值不是一个
     *
     * @return
     */
    public static String try3() {
        String t = "1";
        System.out.println("刚开始是：" + t);
        try {
            t = "try2";
            Integer.parseInt(null);
            System.out.println("执行try方法结果：" + t);

            return t;
        } catch (Exception e) {
            e.printStackTrace();
            t = "catch3";
            System.out.println("执行catch方法结果：" + t);
            return t;
        } finally {
            t = "finally3";
            System.out.println("执行finally方法" + t);

        }

    }

    /**
     * 结果是finally
     * 和方法try2类似，如果同时有try catch 和finally return的话，最后返回finally中的
     *
     * @return
     */
    public static String try4() {
        String t = "1";
        System.out.println("刚开始是：" + t);
        try {
            t = "try2";
            Integer.parseInt(null);
            System.out.println("执行try方法结果：" + t);
            return t;
        } catch (Exception e) {
            e.printStackTrace();
            t = "catch3";
            System.out.println("执行catch方法结果：" + t);
            return t;
        } finally {
            t = "finally3";
            System.out.println("执行finally方法" + t);
            return t;
        }

    }


    /**
     * 如果catch 还有异常怎么办？哈哈
     * <p>
     * 由于try语句抛出异常，程序进入catch语句块，catch语句块又抛出一个异常，
     * 说明catch语句要退出，则执行finally语句块，对t进行赋值。
     * 但是已经没有最后的结果了，最后的结果就是异常
     *
     * @return
     */
    public static String try5() {
        String t = "1";
        System.out.println("刚开始是：" + t);
        try {
            t = "try2";
            Integer.parseInt(null);
            System.out.println("执行try方法结果：" + t);
            return t;
        } catch (Exception e) {
            e.printStackTrace();
            t = "catch3";
            Integer.parseInt(null);
            System.out.println("执行catch方法结果：" + t);
            return t;
        } finally {
            t = "finally3";
            System.out.println("执行finally方法" + t);
        }

    }

    /**
     * 结果是:finally ,而且不像方法5一样抛出异常
     *
     * @return
     */
    public static String try6() {
        String t = "1";
        System.out.println("刚开始是：" + t);
        try {
            t = "try2";
            Integer.parseInt(null);
            System.out.println("执行try方法结果：" + t);
            return t;
        } catch (Exception e) {
            e.printStackTrace();
            t = "catch3";
            Integer.parseInt(null);
            System.out.println("执行catch方法结果：" + t);
            return t;
        } finally {
            t = "finally3";
            System.out.println("执行finally方法" + t);
            return t;
        }

    }

    /**
     * 当前方法测试的是异常捕获不正确的情况
     * 没有结果：抛出了NumberFormatException异常
     *
     * @return
     */
    public static String try7() {
        String t = "1";
        System.out.println("刚开始是：" + t);
        try {
            t = "try2";
            Integer.parseInt(null);
            System.out.println("执行try方法结果：" + t);
            return t;
        } catch (NullPointerException e) {
            e.printStackTrace();
            t = "catch3";
            System.out.println("执行catch方法结果：" + t);
            return t;
        } finally {
            t = "finally3";
            System.out.println("执行finally方法" + t);
        }

    }

    /**
     * 测试的是抛出错误的异常，但是最后finally中有return 的情况
     * 结果是：finally，而且没有异常抛出
     *
     * @return
     */
    public static String try8() {
        String t = "1";
        System.out.println("刚开始是：" + t);
        try {
            t = "try2";
            Integer.parseInt(null);
            System.out.println("执行try方法结果：" + t);
            return t;
        } catch (NullPointerException e) {
            e.printStackTrace();
            t = "catch3";
            System.out.println("执行catch方法结果：" + t);
            return t;
        } finally {
            t = "finally3";
            System.out.println("执行finally方法" + t);
            return t;
        }

    }

    /**
     * 测试主程序没有异常，但是finally中有异常的情况
     * 没有结果，而且最后会抛出异常
     *
     * @return
     */
    public static String try9() {
        String t = "1";
        System.out.println("刚开始是：" + t);
        try {
            t = "try2";
            System.out.println("执行try方法结果：" + t);
            return t;
        } catch (NullPointerException e) {
            e.printStackTrace();
            t = "catch3";
            System.out.println("执行catch方法结果：" + t);
            return t;
        } finally {
            t = "finally3";
            String.valueOf(null);
            System.out.println("执行finally方法" + t);
            return t;
        }

    }

    /**
     * 结论：
     * 1. try、catch、finally语句中，在如果try语句有return语句，则返回的之后当前try中变量此时对应的值，此后对变量做任何的修改，都不影响try中return的返回值
     * 2. 如果finally块中有return 语句，则返回try或catch中的返回语句忽略。
     * 3. 如果finally块中抛出异常，则整个try、catch、finally块中抛出异常
     * 建议：
     * 1.尽量在try或者catch中使用return语句。通过finally块中达到对try或者catch返回值修改是不可行的。
     * 2. finally块中避免使用return语句，因为finally块中如果使用return语句，会显示的消化掉try、catch块中的异常信息，屏蔽了错误的发生
     * 3. finally块中避免再次抛出异常，否则整个包含try语句块的方法回抛出异常，并且会消化掉try、catch块中的异常
     *
     * @param args
     */
    public static void main(String[] args) {
        System.out.println("1------------------------------");
        System.out.println("最后结果是：" + try1());
        System.out.println("2------------------------------");
        System.out.println("最后结果是：" + try2());
        System.out.println("3------------------------------");
        System.out.println("最后结果是：" + try3());
        System.out.println("4------------------------------");
        System.out.println("最后结果是：" + try4());
        System.out.println("5------------------------------");
//        System.out.println("最后结果是：" + try5());
        System.out.println("6------------------------------");
        System.out.println("最后结果是：" + try6());
        System.out.println("7------------------------------");
//        System.out.println("最后结果是：" + try7());
        System.out.println("8------------------------------");
        System.out.println("最后结果是：" + try8());
        System.out.println("9------------------------------");
        System.out.println("最后结果是：" + try9());
    }

}
