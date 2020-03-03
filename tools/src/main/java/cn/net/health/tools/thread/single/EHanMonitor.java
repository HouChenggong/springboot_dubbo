package cn.net.health.tools.thread.single;

public class EHanMonitor {
    /**
     * todo-xiyou 恶汉模式
     * 1. 如果在恶汉单例模式里面添加一个它的全局属性
     * 2. 如果想获取它的yourClass 可以调用直接调用yourClass
     * 3. 但是这样对象会初始化，构造方法初始化，我不想让类初始化怎么办
     * 4. 但是也只会初始化一次
     */
    public static String yourClass = "恶汉模式，通信工程1班";


    private static EHanMonitor classMonitor = new EHanMonitor();

    private EHanMonitor() {
        System.out.println("恶汉模式：构造方法初始化，只会初始化一次" + yourClass);
    }

    public static EHanMonitor getClassMonitor() {
        System.out.println("恶汉模式,获取对象" + classMonitor);
        return classMonitor;
    }

    public static void main(String[] args) {
        System.out.println(EHanMonitor.yourClass );
        System.out.println("上面是只调用单例模式的属性，但是也会进行类的初始化");
        System.out.println(EHanMonitor.yourClass + getClassMonitor());
        System.out.println(EHanMonitor.yourClass + EHanMonitor.getClassMonitor());
    }
}
