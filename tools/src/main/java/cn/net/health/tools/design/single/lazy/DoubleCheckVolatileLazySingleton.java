package cn.net.health.tools.design.single.lazy;


/**
 * @author xiyou
 * /*
 * 1. 最终版本的懒汉单例模式
 * 2. 双重校验的volatile懒汉单例模式
 * 3. 添加volatile 是为了防止类初始化的时候出现问题
 * 4. 类初始化顺序：monitor =new Monitor();
 * 3.1 在堆内存分配空间
 * 3.2 把Monitor的构造方法初始化
 * 3.3 把monitor对象指向在堆空间分配好的地址空间
 * 5. 在多线程条件下，3.2 和3.3的顺序可能互调，volatile就说为了解决这个问题的
 * 6. 故 它是线程安全的
 */
public class DoubleCheckVolatileLazySingleton {

    private static volatile DoubleCheckVolatileLazySingleton DOUBLE_CHECK_VOLATILE_LZY_INSTANCE = null;

    private DoubleCheckVolatileLazySingleton() {
    }

    public static DoubleCheckVolatileLazySingleton getMonitor() {
        //外层判断用来判断是否要阻塞线程，提高效率，外层的可以去掉，但是会影响效率
        if (DOUBLE_CHECK_VOLATILE_LZY_INSTANCE == null) {
            synchronized (DoubleCheckVolatileLazySingleton.class) {
                //内存判断是否要创建线程，保证线程安全，这个不能去掉，去掉线程不安全
                if (null == DOUBLE_CHECK_VOLATILE_LZY_INSTANCE) {
                    DOUBLE_CHECK_VOLATILE_LZY_INSTANCE = new DoubleCheckVolatileLazySingleton();
                }
            }
        }
        return DOUBLE_CHECK_VOLATILE_LZY_INSTANCE;
    }

}