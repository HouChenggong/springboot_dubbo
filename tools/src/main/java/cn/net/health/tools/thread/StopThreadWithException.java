package cn.net.health.tools.thread;

/**
 * 建议使用“抛异常”的方法来实现线程的停止，
 * 因为在catch块中还可以将异常向上抛，使线程停止事件得以传播。
 * https://mp.weixin.qq.com/s/bkIoi4Hro2HrH_dr7WHHIA
 */
public class StopThreadWithException  extends Thread{
    public void run(){
        super.run();
        try {
            for(int i=0; i<5000000; i++){
                if(this.interrupted()) {
                    System.out.println("线程已经终止， for循环不再执行");
                    throw new InterruptedException();
                }
                System.out.println("i="+(i+1));
            }

            System.out.println("这是for循环外面的语句，也会被执行");
        } catch (InterruptedException e) {
            System.out.println("进入MyThread.java类中的catch了。。。");
            e.printStackTrace();
        }
    }
    public static void main(String args[]){
        Thread thread = new StopThreadWithException();
        thread.start();
        try {
            Thread.sleep(1000);
            thread.interrupt();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
