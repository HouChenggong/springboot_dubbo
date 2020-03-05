package cn.net.health.tools.design.single.hungry;

/**
 * @author xiyou
 * SimpleHungrySingleton的变种，其实它一样，没啥区别，就是在构造函数里面初始化
 */


public class StaticHungrySingleton {

    //类加载的时候，单例已经创建
    private static StaticHungrySingleton STATIC_HUNGRY_INSTANCE;

    //静态代码块，其实没啥用，只是spring先属性后方法，先静态后动态，先上后下
    private StaticHungrySingleton() {
        STATIC_HUNGRY_INSTANCE = new StaticHungrySingleton();
    }

    //提供全局访问点
    public static StaticHungrySingleton getEhanMonitor() {
        return STATIC_HUNGRY_INSTANCE;
    }
}