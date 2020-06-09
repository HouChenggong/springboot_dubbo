package cn.net.health.tools.jvm;



public class T {
    public synchronized void tt(){
        System.out.println("...");
    }

    public void test2(){
        synchronized (this){
            System.out.println("2222");
        }
    }

}
