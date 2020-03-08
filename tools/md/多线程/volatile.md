@[toc]

# volatile
##  JVM Happens-before原则：

除了下面的8种：

1. 同一个线程，按照代码顺序
2. 一个unlock操作比lock操作优先
3. volatile 对一个volatile的写操作优先对这个变量的读操作
4.  thread的start（）方法优先当前线程内的任何操作
5. 线程的所有操作比线程的终止检测优先
6. A优先B，B优先C ，那么A优先C

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



#### 2.3 但是不能保证原子性，所以不能++



