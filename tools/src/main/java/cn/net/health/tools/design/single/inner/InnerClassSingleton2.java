package cn.net.health.tools.design.single.inner;

import java.lang.reflect.Constructor;

/**
 * @author xiyou
 * 在构造函数里面判断
 */
public class InnerClassSingleton2 {
    private InnerClassSingleton2() {
        if (LazyHolder.instance != null) {
            throw new RuntimeException("不允许反射创建实例");
        }
    }

    private static final class LazyHolder {
        private static final InnerClassSingleton2 instance = new InnerClassSingleton2();
    }

    public static final InnerClassSingleton2 getInstance() {
        return LazyHolder.instance;
    }
    public static void main(String[] args) {
        Object instance = InnerClassSingleton2.getInstance();
        System.out.println(instance);
        //通过非正常手段，反射来破坏单例
        Class<?> oneClass = InnerClassSingleton2.class;
        try {
            Constructor c = oneClass.getDeclaredConstructor();
            c.setAccessible(true);
            Object instance1 = c.newInstance();
            Object instance2 = c.newInstance();
            Object instance3 = c.newInstance();
            System.out.println(instance1);
            System.out.println(instance2);
            System.out.println(instance3);
            System.out.println(c);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
