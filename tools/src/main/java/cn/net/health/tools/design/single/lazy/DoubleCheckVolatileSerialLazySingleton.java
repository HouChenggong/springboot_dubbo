package cn.net.health.tools.design.single.lazy;


import java.io.*;
import java.lang.reflect.Constructor;

/**
 * 添加readSolve方法，防止序列化的时候破坏单例模式
 * 但是不能阻止反射破坏单例模式
 */
public class DoubleCheckVolatileSerialLazySingleton implements Serializable {

    private static volatile DoubleCheckVolatileSerialLazySingleton DOUBLE_CHECK_VOLATILE_LZY_INSTANCE = null;

    private DoubleCheckVolatileSerialLazySingleton() {
    }

    public static DoubleCheckVolatileSerialLazySingleton getMonitor() {
        //外层判断用来判断是否要阻塞线程，提高效率，外层的可以去掉，但是会影响效率
        if (DOUBLE_CHECK_VOLATILE_LZY_INSTANCE == null) {
            synchronized (DoubleCheckVolatileSerialLazySingleton.class) {
                //内存判断是否要创建线程，保证线程安全，这个不能去掉，去掉线程不安全
                if (null == DOUBLE_CHECK_VOLATILE_LZY_INSTANCE) {
                    DOUBLE_CHECK_VOLATILE_LZY_INSTANCE = new DoubleCheckVolatileSerialLazySingleton();
                }
            }
        }
        return DOUBLE_CHECK_VOLATILE_LZY_INSTANCE;
    }

    //能阻止反射破坏单例模式
    private Object readResolve() {
        return DOUBLE_CHECK_VOLATILE_LZY_INSTANCE;
    }

    public static void main(String[] args) throws Exception {

        //先测试被序列化的对象是否是单例模式
        Constructor<DoubleCheckVolatileSerialLazySingleton> constructor = DoubleCheckVolatileSerialLazySingleton.class.getDeclaredConstructor();
        constructor.setAccessible(true);
        DoubleCheckVolatileSerialLazySingleton s1 = constructor.newInstance();
        DoubleCheckVolatileSerialLazySingleton s2 = constructor.newInstance();
        System.out.println(s1 == s2);


        //Write Obj to file
        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("tempFile"));
        oos.writeObject(DoubleCheckVolatileSerialLazySingleton.getMonitor());
        //Read Obj from file
        File file = new File("tempFile");
        ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file));
        DoubleCheckVolatileSerialLazySingleton newInstance = (DoubleCheckVolatileSerialLazySingleton) ois.readObject();
        //判断是否是同一个对象
        System.out.println(newInstance == DoubleCheckVolatileSerialLazySingleton.getMonitor());
    }


}