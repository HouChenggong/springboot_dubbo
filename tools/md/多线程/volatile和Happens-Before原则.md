@[toc]

# volatile
##  JVM Happens-before原则：

```
有序性是指，在JMM中，允许编译器和处理器对指令进行重排序，但是重排序过程不会影响到单线程程序的执行，却会影响到多线程并发执行的正确性。
 
另外，JMM具有先天的有序性，即不需要通过任何手段就可以得到保证的有序性。这称为happens-before原则。

如果两个操作的执行次序无法从happens-before原则推导出来，那么它们就不能保证它们的有序性。虚拟机可以随意地对它们进行重排序。

happens-before原则：

1.程序次序规则：在一个单独的线程中，按照程序代码书写的顺序执行。

2.锁定规则：一个unlock操作happen—before后面对同一个锁的lock操作。

3.volatile变量规则：对一个volatile变量的写操作happen—before后面对该变量的读操作。

4.线程启动规则：Thread对象的start()方法happen—before此线程的每一个动作。

5.线程终止规则：线程的所有操作都happen—before对此线程的终止检测，可以通过Thread.join()方法结束、Thread.isAlive()的返回值等手段检测到线程已经终止执行。

6.线程中断规则：对线程interrupt()方法的调用happen—before发生于被中断线程的代码检测到中断时事件的发生。

7.对象终结规则：一个对象的初始化完成（构造函数执行结束）happen—before它的finalize()方法的开始。

8.传递性：如果操作A happen—before操作B，操作B happen—before操作C，那么可以得出A happen—before操作C。

 

​```
```



## 1. volatile的5层实现

volatile int i ——ACC_VOLATILE_JVM——JVM内存屏障——汇编语言调用——MESI原语支持总线锁

#### 1.1 Java源码

添加volatile

#### 1.2 ByteCode源码

加volatile之后，只是带一个了ACC_VOLATILE标识值为：**0x0040[volatile]**

#### 1.3 JVM层面

添加了内存屏障

内存屏障的两端无法重排序的操作

volatile写操作storeStoreBarrier    storeLoadBarrier 

volatile读操作 LoadStoreBarrier   loadLoadBarrier

![](/img/内存屏障.png)







#### 1.4 HotSpot实现（JVM的一种，是Oracle提供的）

实际用了汇编语言的调用，比如Lock指令

#### 1.5CPU级别 

1. MESI Cache 缓存一致性协议

如果一个CPU的值发生改变，就会通知其它CPU当前值的状态发生改变了，然后其它CPU需要访问这个值的时候，发现要访问的那个值的状态发生改变了，就会从内存上重新读取值

2. 原语支持
3. 总线帧

## 2.valatile作用

#### 2.1 指令可见性

 如果不是可见性，下面将死循环，但是如果是可见的，在程序最后的**falg = false;**就会被线程观测到

```java
public class VolatileTest {

    public static volatile Boolean falg = true;

    public static void main(String[] args) {
        new Thread(() -> {
            while (falg) {
//               doSomething
            }
            System.out.println("end");
        }, "server").start();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        falg = false;
    }
}
```



#### 2.2 禁止指令重排序

指令可以重排序的地方：new Object()版初始化重排序

int a =7;int b =4;

int c=a+b ,取ab值的时候，可以先取b再取a

​                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                               

#### 2.3 但是不能保证原子性，所以不能++



## 3. 在32虚拟机 long double不是原子性的

但是在32位的操作系统中，加volatile可以保证long 和double的原子性，其实没有意义



## 4. volatile真正用途——布尔值

