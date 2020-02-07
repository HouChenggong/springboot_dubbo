package cn.net.health.tools.thread.single;

/**
 * todo-xiyou 懒汉单例模式包括DDL
 */
public class Monitor {

/*      1.  最简单的单利模式
        2.  将构造方法私有化，这样是防止在其他地方被实例化，就出现多个班长对象了
        3.  最后给外界提供一个方法，返回这个班长对象即可
        4. 不能保证线程安全问题

    private static Monitor monitor = null;
    private Monitor() {
    }

    public static Monitor getMonitor() {
        if (monitor == null) {
            monitor = new Monitor();
        }
        return monitor;
    }
    */



/*
        1. 相对于上面多了一个synchronized 线程安全了
        2.  但是效率太差了，不管班长对象有没有被创建好，后面每个线程并发走到这，无用等待太多了

    private static Monitor monitor =null ;
    private  Monitor (){

    }
    public  synchronized  static  Monitor getMonitor(){
        if(monitor ==null){
            monitor =new Monitor();
        }
        return monitor;
    }

*/


/*
	DCL 双重校验实现的单例模式

    1. 我们不在方法上添加 synchronized关键字，但可以在方法内部添加

    2. 这样效率就会高很多


    3. 但是线程是非安全的

    private  static  Monitor monitor =null ;

    private  Monitor (){}

    public   static  Monitor getMonitor(){
        if(monitor ==null){
            synchronized (Monitor.class){
                if(monitor ==null){
                    monitor =new Monitor();
                }
            }
        }
        return monitor ;
    }

*/

    /*
        1. 最终版本的懒汉单例模式
        2. 双重校验的volatile懒汉单例模式
        3. 添加volatile 是为了防止类初始化的时候出现问题
        4. 类初始化顺序：monitor =new Monitor();
            3.1 在堆内存分配空间
            3.2 把Monitor的构造方法初始化
            3.3 把monitor对象指向在堆空间分配好的地址空间
        5. 在多线程条件下，3.2 和3.3的顺序可能互调，volatile就说为了解决这个问题的
        6. 故 它是线程安全的
     */

    private static volatile Monitor monitor = null;

    private Monitor() {
        System.out.println("双重校验的volatile懒汉单例模式初始化");
    }

    public static Monitor getMonitor() {
        if (monitor == null) {
            synchronized (Monitor.class) {
                if (monitor == null) {
                    monitor = new Monitor();
                }
            }
        }
        return monitor;
    }

    public static void main(String[] args) {
        getMonitor();
        getMonitor();
    }
}

