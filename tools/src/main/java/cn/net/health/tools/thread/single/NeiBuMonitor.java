package cn.net.health.tools.thread.single;

/**
 * todo-xiyou 静态内部类实现单例模式
 */
public class NeiBuMonitor {

    /*
  1. 当执行getInstance()的时候才会调用内部类里面的NeiBuMonitor实例
  2. 此时，内部类会被加载到内存中，在类加载的时候对monitor进行初始化
  3. 而且直接调用YOUR_CLASS的时候不会对类初始化
  4. 这是懒汉模式和恶汉模式的结合体，而且线程安全
*/
    public static String YOUR_CLASS = "静态内部类：通信工程";

    //静态内部类，用来创建班长对象
    private static class MonitorCreator {
        private static NeiBuMonitor CLASS_MONITOR = new NeiBuMonitor();
    }

    private NeiBuMonitor() {
        System.out.println("静态内部类：构造方法初始化,但是只会初始化一次");
    }

    public static NeiBuMonitor getInstance() {
        System.out.println("静态内部类获取:单例模式");
        return MonitorCreator.CLASS_MONITOR;
    }

    public static void main(String[] args) {
        System.out.println(YOUR_CLASS);
        System.out.println(NeiBuMonitor.YOUR_CLASS);
        System.out.println("上面只是获取静态内部类的属性，看看会不会进行类的初始化，发现不会进行初始化");
        System.out.println("内存地址" + getInstance());
        System.out.println("内存地址" + getInstance());
    }
}
