package cn.net.health.tools.design.single.hungry;

public class StaticPropertyHungry {
    /**
     * todo-xiyou 饿汉模式
     * 1. 如果在饿汉单例模式里面添加一个它的全局属性
     * 2. 如果想获取它的yourClass 可以调用直接调用yourClass
     * 3. 但是这样对象会使当前对象初始化（构造方法初始化，虽然指挥初始化一次），但我还是不想让类初始化怎么办
     * 4.
     */
    public static final String YOUR_PROPERTY = "xiyou";


    private static StaticPropertyHungry classMonitor = new StaticPropertyHungry();

    private StaticPropertyHungry() {
        System.out.println("饿汉模式：构造方法初始化，只会初始化一次" + YOUR_PROPERTY);
    }

    public static StaticPropertyHungry getClassMonitor() {
        System.out.println("饿汉模式,获取对象" + classMonitor);
        return classMonitor;
    }

    public static void main(String[] args) {
        System.out.println(StaticPropertyHungry.YOUR_PROPERTY);
        System.out.println(StaticPropertyHungry.YOUR_PROPERTY);
        System.out.println("上面是只调用单例模式的属性，但是也会进行类的初始化");
        System.out.println(StaticPropertyHungry.YOUR_PROPERTY + getClassMonitor());
        System.out.println(StaticPropertyHungry.YOUR_PROPERTY + StaticPropertyHungry.getClassMonitor());
    }
}
