package cn.net.health.tools.javaclass;

import static java.lang.Class.forName;

public class MyClassForName {
    static {
        System.out.println("静态代码初始化");
    }

    private static String staticFiled = staticMethod();

    private static String staticMethod() {
        System.out.println("静态方法");
        return "静态方法赋值";
    }

/**
 * 下面的代码可以带入其他类的主方法中运行，结构表明：
 * Class.forName 加载类是将类进了初始化，而 ClassLoader 的 loadClass 并没有对类进行初始化，只是把类加载到了虚拟机中。
 * 在我们熟悉的 Spring 框架中的 IOC 的实现就是使用的 ClassLoader。
 *
 * 而在我们使用 JDBC 时通常是使用 Class.forName() 方法来加载数据库连接驱动。
 * 这是因为在 JDBC 规范中明确要求 Driver(数据库驱动)类必须向 DriverManager 注册自己。
          try {
               System.out.println("......................");
               Class.forName("cn.net.health.tools.javaclass.MyClassForName");
             System.out.println("————————————————————————————");
              ClassLoader.getSystemClassLoader().loadClass("cn.net.health.tools.javaclass.MyClassForName");
          }catch (ClassNotFoundException e){
              e.printStackTrace();
          }
 */
}
