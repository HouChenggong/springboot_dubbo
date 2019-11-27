package cn.net.health.tools.lambda.celue;

/**
 * @author xiyou
 * @version 1.2
 * @date 2019/11/27 15:54
 */
public interface VaildationStrategy {
    /**
     * 执行策略模式vaildation验证的接口
     *
     * @param s
     * @return
     */
    Boolean execute(String s);
}
