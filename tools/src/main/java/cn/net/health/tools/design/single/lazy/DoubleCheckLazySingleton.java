package cn.net.health.tools.design.single.lazy;


/**
 * @author xiyou
 * 1. 这个双重判断看似线程安全，但还是有线程安全问题
 */
public class DoubleCheckLazySingleton {

    private static DoubleCheckLazySingleton DOUBLE_CHECK_LAZY_INSTANCE = null;

    private DoubleCheckLazySingleton() {
    }

    public static DoubleCheckLazySingleton getMonitor() {
        //外层判断用来判断是否要阻塞线程，提高效率，外层的可以去掉，但是会影响效率
        if (DOUBLE_CHECK_LAZY_INSTANCE == null) {
            synchronized (DoubleCheckLazySingleton.class) {
                //内存判断是否要创建线程，保证线程安全，这个不能去掉，去掉线程不安全
                if (null == DOUBLE_CHECK_LAZY_INSTANCE) {
                    DOUBLE_CHECK_LAZY_INSTANCE = new DoubleCheckLazySingleton();
                }
            }
        }
        return DOUBLE_CHECK_LAZY_INSTANCE;
    }

}