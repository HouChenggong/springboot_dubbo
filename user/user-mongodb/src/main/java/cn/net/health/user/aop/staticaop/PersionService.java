package cn.net.health.user.aop.staticaop;

/**
 * @author xiyou
 * 顶层接口
 */
public interface PersionService {
    /**
     * 模拟静态代理
     * @param name
     */
    void  sayHello(String name);
}
