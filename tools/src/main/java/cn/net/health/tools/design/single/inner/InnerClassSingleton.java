package cn.net.health.tools.design.single.inner;

import java.lang.reflect.Constructor;

/**
 * @author xiyou
 * 静态内部类实现单例模式
 * 看似近似完美，但是其实还是有问题的，比如反射破坏单例
 */
public class InnerClassSingleton {

    private InnerClassSingleton() {

    }

    private static final class LazyHolder {
        private static final InnerClassSingleton INNER_INSTANCE = new InnerClassSingleton();
    }

    public static final InnerClassSingleton getInstance() {
        return LazyHolder.INNER_INSTANCE;
    }

    public static void main(String[] args) {
        Object instance = InnerClassSingleton.getInstance();
        System.out.println(instance);
        //通过非正常手段，反射来破坏单例
        Class<?> oneClass = InnerClassSingleton.class;
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
    /**
     * 结果：发现已经破坏单例模式了
     * cn.net.health.tools.design.single.lazy.inner.InnerClassSingleton@783e6358
     * cn.net.health.tools.design.single.lazy.inner.InnerClassSingleton@17550481
     * cn.net.health.tools.design.single.lazy.inner.InnerClassSingleton@735f7ae5
     * cn.net.health.tools.design.single.lazy.inner.InnerClassSingleton@180bc464
     * private cn.net.health.tools.design.single.lazy.inner.InnerClassSingleton()
     */
}
