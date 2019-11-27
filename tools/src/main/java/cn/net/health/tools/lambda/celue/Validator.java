package cn.net.health.tools.lambda.celue;

/**
 * @author xiyou
 * @version 1.2
 * @date 2019/11/27 15:55
 * 策略模式的实现类
 */
public class Validator {


    private final VaildationStrategy strategy;

    public Validator(VaildationStrategy strategy) {
        this.strategy = strategy;
    }

    /**
     * lambda策略模式函数式接口
     *
     * @param s
     * @return
     */
    public boolean validate(String s) {
        return strategy.execute(s);
    }

    /**
     * 策略模式严重的方式
     *
     * @param args
     */
    public static void main(String[] args) {
        Validator validator = new Validator((String s) -> {
            //匹配数字
            return s.matches("\\d+");
        });
        Boolean one = validator.validate("7889");
        System.out.println(one);
        Validator validator2 = new Validator((String s) -> {
            //可以匹配至少由一个数字、字母或者下划线组成的字符串，比如'a100'，'0_Z'，'Py3000'等等；
            return s.matches("[0-9a-zA-Z\\_]+");
        });
        Boolean one2 = validator2.validate("##");
        Boolean one3 = validator2.validate("_222");
        System.out.println(one2);
        System.out.println(one3);
        //结果是：true false true
    }

}
