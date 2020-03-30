package cn.net.health.user.aop.dynamic;

/**
 * @author xiyou
 * JDK动态代理
 */
public interface DataChangeListener {
    /**
     * When db changed, we will callback this method to execute custom business.
     * Warning: be careful blocking thread.
     *
     * @param obj Model of db declare
     */
    void listener(Object obj);

    /**
     * 测试JDK动态代理
     *
     * @param name
     * @return
     */
    String sayHello(String name);


    /**
     * 测试JDK动态代理
     *
     * @param name
     * @return
     */
    String sayHello2(String name);

}
