package cn.net.health.tools.lambda.zeren;

import java.util.function.Function;
import java.util.function.UnaryOperator;

/**
 * @author xiyou
 * @version 1.2
 * @date 2019/11/27 16:18
 * 责任链模式使用lambda来处理
 * <p>
 * 责任链模式在实际中的应用，其实就是那些拦截器和过滤器，比如： Mybatis 拦截器
 */
public class ZeRenWithLambda {

    /**
     * 函数式编程思路
     * 如果使用函数式编程思维，那么职责链模式就直接了——y=f(x)和z=g(x)这两个方法都是要对x做处理，
     * 那么如果将这两个函数组合在一起，就会形成r=f(g(x))的情况，
     * 也就是可以使用Lambda表达式中的addThen来串联起多个处理过程。
     *
     * @param args
     */
    public static void main(String[] args) {
        UnaryOperator<String> one = (String text) -> " oneTxt   " + text;
        UnaryOperator<String> two = (String text) -> text.replace("one", "111");
        Function<String, String> function = one.andThen(two);
        String result = function.apply("function  one and two");
        System.out.println(result);


        ///java8 lambda表达式
        UnaryOperator<String> headerProcessing =
                (String text) -> "From Raoul, Mario and Alan: " + text;
        UnaryOperator<String> spellCheckerProcessing =
                (String text) -> text.replaceAll("labda", "lambda");
        UnaryOperator<String> lengthProcesing =
                (String text) -> text + " ->length:" + text.length();
        ///函数复合
        Function<String, String> pipeline =
                headerProcessing.andThen(spellCheckerProcessing).andThen(lengthProcesing);
        String res = pipeline.apply("Aren't labdas really sexy?!!");
        System.out.println(res);


    }

}
