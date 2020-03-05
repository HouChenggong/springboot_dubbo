package cn.net.health.tools.design.single.hungry;

/**
 * @author xiyou
 * 最简单的饿汉单例
 */


public class SimpleHungrySingleton {

    //类加载的时候，单例已经创建
    private static SimpleHungrySingleton SIMPLE_HUNGRY_INSTANCE = new SimpleHungrySingleton();

    //构造函数私有化
    private SimpleHungrySingleton() {
    }

    //提供全局访问点
    public static SimpleHungrySingleton getEhanMonitor() {
        return SIMPLE_HUNGRY_INSTANCE;
    }
}