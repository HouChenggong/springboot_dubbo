### 1. 重载和重写的区别

#### 重载

发生在同一个类中，方法名必须相同，参数类型不同、个数不同、顺序不同，方法返回值和访问修饰符可以不同。

下面是《Java 核心技术》对重载这个概念的介绍：

![](https://my-blog-to-use.oss-cn-beijing.aliyuncs.com/bg/desktopjava核心技术-重载.jpg)

#### 重写

重写是子类对父类的允许访问的方法的实现过程进行重新编写,发生在子类中，方法名、参数列表必须相同，返回值范围小于等于父类，抛出的异常范围小于等于父类，访问修饰符范围大于等于父类。另外，如果父类方法访问修饰符为 private 则子类就不能重写该方法。**也就是说方法提供的行为改变，而方法的外貌并没有改变。**



### 2. String StringBuffer 和 StringBuilder  

**可变性**

简单的来说：String 类中使用 final 关键字修饰字符数组来保存字符串，`private final char value[]`，所以 String 对象是不可变的。

> 补充（来自[issue 675](https://github.com/Snailclimb/JavaGuide/issues/675)）：在 Java 9 之后，String 类的实现改用 byte 数组存储字符串 `private final byte[] value`

而 StringBuilder 与 StringBuffer 都继承自 AbstractStringBuilder 类，在 AbstractStringBuilder 中也是使用字符数组保存字符串`char[]value` 但是没有用 final 关键字修饰，所以这两种对象都是可变的。

StringBuilder 与 StringBuffer 的构造方法都是调用父类构造方法也就是 AbstractStringBuilder 实现的，大家可以自行查阅源码。

`AbstractStringBuilder.java`

```java
abstract class AbstractStringBuilder implements Appendable, CharSequence {
    /**
     * The value is used for character storage.
     */
    char[] value;

    /**
     * The count is the number of characters used.
     */
    int count;

    AbstractStringBuilder(int capacity) {
        value = new char[capacity];
    }
```

**线程安全性**

String 中的对象是不可变的，也就可以理解为常量，线程安全。AbstractStringBuilder 是 StringBuilder 与 StringBuffer 的公共父类，定义了一些字符串的基本操作，如 expandCapacity、append、insert、indexOf 等公共方法。StringBuffer 对方法加了同步锁或者对调用的方法加了同步锁，所以是线程安全的。StringBuilder 并没有对方法进行加同步锁，所以是非线程安全的。

**性能**

每次对 String 类型进行改变的时候，都会生成一个新的 String 对象，然后将指针指向新的 String 对象。StringBuffer 每次都会对 StringBuffer 对象本身进行操作，而不是生成新的对象并改变对象引用。相同情况下使用 StringBuilder 相比使用 StringBuffer 仅能获得 10%~15% 左右的性能提升，但却要冒多线程不安全的风险。

**对于三者使用的总结：**

1. 操作少量的数据: 适用 String
2. 单线程操作字符串缓冲区下操作大量数据: 适用 StringBuilder
3. 多线程操作字符串缓冲区下操作大量数据: 适用 StringBuffer

### 3. == 与 equals(重要)

**==** : 它的作用是判断两个对象的地址是不是相等。即，判断两个对象是不是同一个对象(基本数据类型==比较的是值，引用数据类型==比较的是内存地址)。

**equals()** : 它的作用也是判断两个对象是否相等。但它一般有两种使用情况：

- 情况 1：类没有覆盖 equals() 方法。则通过 equals() 比较该类的两个对象时，等价于通过“==”比较这两个对象。
- 情况 2：类覆盖了 equals() 方法。一般，我们都覆盖 equals() 方法来比较两个对象的内容是否相等；若它们的内容相等，则返回 true (即，认为这两个对象相等)。

**举个例子：**

```java
public class test1 {
    public static void main(String[] args) {
        String a = new String("ab"); // a 为一个引用
        String b = new String("ab"); // b为另一个引用,对象的内容一样
        String aa = "ab"; // 放在常量池中
        String bb = "ab"; // 从常量池中查找
        if (aa == bb) // true
            System.out.println("aa==bb");
        if (a == b) // false，非同一对象
            System.out.println("a==b");
        if (a.equals(b)) // true
            System.out.println("aEQb");
        if (42 == 42.0) { // true
            System.out.println("true");
        }
    }
}
```

**说明：**

- String 中的 equals 方法是被重写过的，因为 object 的 equals 方法是比较的对象的内存地址，而 String 的 equals 方法比较的是对象的值。
- 当创建 String 类型的对象时，虚拟机会在常量池中查找有没有已经存在的值和要创建的值相同的对象，如果有就把它赋给当前引用。如果没有就在常量池中重新创建一个 String 对象。

### 4. 常量池

**基本类型有**：byte、short、char、int、long、boolean。
**基本类型的包装类分别是**：Byte、Short、Character、Integer、Long、Boolean。

对于int short long  byte 都是-128 到217之间

但是对于double没有实现常量池技术

- == 和equal在数字类型的比较
  - 当 "=="运算符的两个操作数都是 包装器类型的引用，则是比较指向的是否是同一个对象，而如果其中有一个操作数是表达式（即包含算术运算）则比较的是数值（即会触发自动拆箱的过程）

```java
     Integer a = 1;
        Integer b = 2;
        Integer c = 3;
        System.out.println(c == (a + b));//有运算符号，==比较的变为值，所以是true
        System.out.println(c.equals(a + b));// true
        Integer d = 3;
        System.out.println(c == d);//常量池相等true
        Integer e = 321;
        Integer f = 321;
        System.out.println(e == f);//超过常量池范围false
        Long g = 3L;
        System.out.println(g == (a + b));//比较的是值，拆箱true
        Long h = 2L;
        //如果数值是int类型的，装箱过程调用的是Integer.valueOf；如果是long类型的，装箱调用的Long.valueOf方法
        //之所以不相等是因为一个是Long 一个是Integer
        System.out.println(g.equals(a + b));//false
        //a+h会变成Long类型，所以相等
        System.out.println(g.equals(a + h));//true

```

###  5. hashCode 与 equals (重要)

面试官可能会问你：“你重写过 hashcode 和 equals 么，为什么重写 equals 时必须重写 hashCode 方法？”

#### hashCode（）介绍

hashCode() 的作用是获取哈希码，也称为散列码；它实际上是返回一个 int 整数。这个哈希码的作用是确定该对象在哈希表中的索引位置。hashCode() 定义在 JDK 的 Object.java 中，这就意味着 Java 中的任何类都包含有 hashCode() 函数。

散列表存储的是键值对(key-value)，它的特点是：能根据“键”快速的检索出对应的“值”。这其中就利用到了散列码！（可以快速找到所需要的对象）

#### 为什么要有 hashCode

**我们先以“HashSet 如何检查重复”为例子来说明为什么要有 hashCode：** 当你把对象加入 HashSet 时，HashSet 会先计算对象的 hashcode 值来判断对象加入的位置，同时也会与该位置其他已经加入的对象的 hashcode 值作比较，如果没有相符的 hashcode，HashSet 会假设对象没有重复出现。但是如果发现有相同 hashcode 值的对象，这时会调用 `equals()`方法来检查 hashcode 相等的对象是否真的相同。如果两者相同，HashSet 就不会让其加入操作成功。如果不同的话，就会重新散列到其他位置。（摘自我的 Java 启蒙书《Head first java》第二版）。这样我们就大大减少了 equals 的次数，相应就大大提高了执行速度。

通过我们可以看出：`hashCode()` 的作用就是**获取哈希码**，也称为散列码；它实际上是返回一个 int 整数。这个**哈希码的作用**是确定该对象在哈希表中的索引位置。**`hashCode()`在散列表中才有用，在其它情况下没用**。在散列表中 hashCode() 的作用是获取对象的散列码，进而确定该对象在散列表中的位置。

#### hashCode（）与 equals（）的相关规定

1. 如果两个对象相等，则 hashcode 一定也是相同的
2. 两个对象相等,对两个对象分别调用 equals 方法都返回 true
3. 两个对象有相同的 hashcode 值，它们也不一定是相等的
4. **因此，equals 方法被覆盖过，则 hashCode 方法也必须被覆盖**
5. hashCode() 的默认行为是对堆上的对象产生独特值。如果没有重写 hashCode()，则该 class 的两个对象无论如何都不会相等（即使这两个对象指向相同的数据）

推荐阅读：[Java hashCode() 和 equals()的若干问题解答](https://www.cnblogs.com/skywang12345/p/3324958.html)

### 6. 为什么 Java 中只有值传递？

[为什么 Java 中只有值传递？](https://juejin.im/post/5e18879e6fb9a02fc63602e2)

[为啥只有值传参](<https://blog.csdn.net/qq_39455116/article/details/83617271>)

总结：

```java
在Java中所有的参数传递，不管基本类型还是引用类型，
	都是值传递，或者说是副本传递。
	只是在传递过程中：

A:如果是对基本数据类型的数据进行操作，
    由于原始内容和副本都是存储实际值，并且是在不同的栈区，
    因此形参的操作，不影响原始内容。

B:如果是对引用类型的数据进行操作，分两种情况，
	(1)一种是形参和实参保持指向同一个对象地址，则形参的操作，
		会影响实参指向的对象的内容。
	(2)一种是形参被改动指向新的对象地址（如重新赋值引用），
		则形参的操作，不会影响实参指向的对象的内容。
```

为什么？

```java
1. 当运行一个main()方法的时候，会往虚拟机栈中压入一个栈帧，
    即为当前栈帧（Main栈帧），用来存放main()中的局部变量表
    (包括参数)、操作栈、方法出口等信息 
2.而传入另一个方法中的值是在当前栈帧（main栈帧）中的

3.当执行到myTest（）或者myTest2()方法时，
JVM也会往虚拟机栈栈中压入一个栈，即为myTest()栈帧或者myTest2()栈帧
    这个栈帧用来存储这个方法中的局部变量信息，
    所以，当前栈帧操作的数据是在当前栈帧里面，只是"值"是main栈帧的副本
4.而我们知道，同一个栈中的数据可以共享，但是不同栈中的数据不能共享
	这也是为什么所有的传参都说副本传参的原因
```



### 7. 深拷贝 vs 浅拷贝

1. **浅拷贝**：对基本数据类型进行值传递，对引用数据类型进行引用传递般的拷贝，此为浅拷贝。

   - 实现方式：1. 实现Cloneable接口，在bean里面重写clone()方法，权限为public。
   - 拷贝构造函数实现

   ```java
       public Teacher(Age age,String name) {
           this.age=age;
           this.name=name;
       }
   ```

2. **深拷贝**：对基本数据类型进行值传递，对引用数据类型，创建一个新的对象，并复制其内容，此为深拷贝。

   - 方式1：A方案.深复制条件 ---父类和子类（所有涉及到的类）都要实现Cloneable（）接口

   - 方式1 B方案. 父类重写Clone接口的时候，要把子类带上

   - 方式二：涉及到的类都去实现序列化接口

[详细解读](<https://blog.csdn.net/qq_39455116/article/details/82886328>)

###  8. switch 对于string的支持

- **switch对int的判断是直接比较整数的值**
- 对char类型进行比较的时候，实际上比较的是ascii码，编译器会把char型变量转换成对应的int型变量
- 原来字符串的switch是通过`equals()`和`hashCode()`方法来实现的。。**记住，switch中只能使用整型**

**其实switch只支持一种数据类型，那就是整型，其他数据类型都是转换成整型之后在使用switch的。**

### 9. 快速失败、快速安全

- fail fast 

其实就是如果遇到错误，直接返回失败

```java
public int divide(int divisor,int dividend){
    if(dividend == 0){
        throw new RuntimeException("dividend can't be null");
    }
    return divisor/dividend;
}
```

- 集和中的快速失败

在Java中， 如果在foreach 循环里对某些集合元素进行元素的 remove/add 操作的时候，就会触发fail-fast机制，进而抛出CMException。

```java
 List<String> userNames = new ArrayList<String>() {{
            add("Hollis");
            add("hollis");
            add("HollisChuang");
            add("H");
        }};

        for (String userName : userNames) {
            if (userName.equals("Hollis")) {
                userNames.remove(userName);
            }
        }

        System.out.println(userNames);
//Exception in thread "main" java.util.ConcurrentModificationException
```

简单总结一下，之所以会抛出CMException异常，是因为我们的代码中使用了增强for循环，而在增强for循环中，集合遍历是通过iterator进行的，但是元素的add/remove却是直接使用的集合类自己的方法。这就导致iterator在遍历的时候，会发现有一个元素在自己不知不觉的情况下就被删除/添加了，就会抛出一个异常，用来提示用户，可能发生了并发修改！

所以，在使用Java的集合类的时候，如果发生CMException，优先考虑fail-fast有关的情况，实际上这里并没有真的发生并发，只是Iterator使用了fail-fast的保护机制，只要他发现有某一次修改是未经过自己进行的，那么就会抛出异常。

- 为什么阿里巴巴禁止在 foreach 循环里进行元素的 remove/add 操作



- fail safe

为了避免触发fail-fast机制，导致异常，我们可以使用Java中提供的一些采用了fail-safe机制的集合类。

这样的集合容器在遍历时不是直接在集合内容上访问的，而是先复制原有集合内容，在拷贝的集合上进行遍历。

java.util.concurrent包下的容器都是fail-safe的，可以在多线程下并发使用，并发修改。同时也可以在foreach中进行add/remove 。

```java
public static void main(String[] args) {
    List<String> userNames = new CopyOnWriteArrayList<String>() {{
        add("Hollis");
        add("hollis");
        add("HollisChuang");
        add("H");
    }};

    userNames.iterator();

    for (String userName : userNames) {
        if (userName.equals("Hollis")) {
            userNames.remove(userName);
        }
    }

    System.out.println(userNames);
}

```

以上代码，使用CopyOnWriteArrayList代替了ArrayList，就不会发生异常。

fail-safe集合的所有对集合的修改都是先拷贝一份副本，然后在副本集合上进行的，并不是直接对原集合进行修改。并且这些修改方法，如add/remove都是通过加锁来控制并发的。

- copyOnWrite思想

**CopyOnWrite容器是一种读写分离的思想，读和写不同的容器。**

opyOnWriteArrayList中add/remove等写方法是需要加锁的，目的是为了避免Copy出N个副本出来，导致并发写



### 10 枚举

一个普通的枚举

```java
public enum t {
    SPRING,SUMMER;
}
```

反编译后的结果：当我们使用enmu来定义一个枚举类型的时候，编译器会自动帮我们创建一个final类型的类继承Enum类，所以枚举类型不能被继承。

```java
public final class T extends Enum
```

- 枚举为啥线程安全

```java
        public static final T SPRING;
        public static final T SUMMER;

        private static final T ENUM$VALUES[];
        static
        {
            SPRING = new T("SPRING", 0);
            SUMMER = new T("SUMMER", 1);
            ENUM$VALUES = (new T[] {
                SPRING, SUMMER
            });
        }

```



- 枚举序列化线程安全的原因：

当我们使用`enum`来定义一个枚举类型的时候，编译器会自动帮我们创建一个final类型的类继承Enum类,所以枚举类型不能被继承，我们看到这个类中有几个属性和方法。

我们可以看到：都是static类型的，因为static类型的属性会在类被加载之后被初始化，我们在[深度分析Java的ClassLoader机制（源码级别）](https://hollischuang.github.io/archives/199)和[Java类的加载、链接和初始化](https://hollischuang.github.io/archives/201)两个文章中分别介绍过，当一个Java类第一次被真正使用到的时候静态资源被初始化、Java类的加载和初始化过程都是线程安全的。所以， 创建一个enum类型是线程安全的 。

- 枚举序列化线程安全的原因

普通的Java类的反序列化过程中，会通过反射调用类的默认构造函数来初始化对象。所以，即使单例中构造函数是私有的，也会被反射给破坏掉。由于反序列化后的对象是重新new出来的，所以这就破坏了单例。

但是，枚举的反序列化并不是通过反射实现的。所以，也就不会发生由于反序列化导致的单例破坏问题。

why: 为了保证枚举类型像Java规范中所说的那样，每一个枚举类型极其定义的枚举变量在JVM中都是唯一的，在枚举类型的序列化和反序列化上，Java做了特殊的规定 

在序列化的时候Java仅仅是将枚举对象的name属性输出到结果中，反序列化的时候则是通过java.lang.Enum的valueOf方法来根据名字查找枚举对象。同时，编译器是不允许任何对这种序列化机制的定制的，因此禁用了writeObject、readObject、readObjectNoData、writeReplace和readResolve等方法。 

- 枚举比较

java 枚举值比较用 == 和 equals 方法没啥区别，两个随便用都是一样的效果。

因为枚举 Enum 类的 equals 方法默认实现就是通过 == 来比较的；

### 11 反射破坏单例的原因

通过反射获得单例类的构造函数，由于该构造函数是private的，通过setAccessible(true)指示反射的对象在使用时应该取消 Java 语言访问检查,使得私有的构造函数能够被访问，这样使得单例模式失效。

### 12 序列化破坏单例以及阻止序列化破坏单例

列化会通过反射调用无参数的构造方法创建一个新的对象。

- 如何阻止序列化破坏单例,添加下面的方法

```java
    private Object readResolve() {
        return singleton;
    }
```

- 为什么能阻止

但是为啥用了readResolve方法就能是单例了，因为在创建的时候，序列化方法做了一层判断，回去判断是否是readResolve，也就是生成策略会发生变化

- 写了readResolve方法的单例能阻止反射破坏单例吗

不能，因为写了readResolve方法，也只是在序列化的时候动态的判断了一下，并不能阻止反射破坏单例模式

### 13 注解与自定义注解

注解4要素

@Documented –注解是否将包含在JavaDoc中

@Retention –什么时候使用该注解

@Target? –注解用于什么地方

@Inherited – 是否允许子类继承该注解

- 自定义注解

比如接口限流使用令牌桶算法+aop+自定义注解

比如接口防止重复提交用redis+token+aop+自定义注解

- 常用注解

### 14 泛型与类型擦除

- 什么是泛型

就是声明的时候指定具体的类型，泛型最⼤的好处是可以提⾼代码的复⽤性。 以List接⼜为例，我们可以将String、 Integer等类型放⼊List中， 如不⽤泛型， 存放String类型要写⼀个List接口， 存放Integer要写另外⼀个List接口， 泛型可以很好的解决这个问题。

- 什么是类型擦除

擦除的主要过程如下： 

1. 将所有的泛型参数用其最左边界（最顶级的父类型）类型替换

2. 移除所有的类型参数。
3. 其实就是将泛型java代码转换为普通java代码，只不过编译器更直接点，将泛型java代码直接转换成普通java字节码
4. 也就是说Java中的泛型，只在编译阶段有效。在编译过程中，正确检验泛型结果后，会将泛型的相关信息擦出，并且在对象进入和离开方法的边界处添加类型检查和类型转换的方法。也就是说，泛型信息不会进入到运行时阶段。

比如下面的代码

```java
public static void main(String[] args) {  
    Map<String, String> map = new HashMap<String, String>();  
    map.put("name", "hollis");  
    map.put("age", "22");  
    System.out.println(map.get("name"));  
    System.out.println(map.get("age"));  
}  

```

反编译后的代码

执行命令 javac XX.java 然后查看class文件即可

```java
public static void main(String[] args) {  
    Map map = new HashMap();  
    map.put("name", "hollis");  
    map.put("age", "22"); 
    System.out.println((String) map.get("name"));  
    System.out.println((String) map.get("age"));  
}  

```

? 通配符类型 无边界的通配符(Unbounded Wildcards), 就是<?>, 比如List<?>
　　     无边界的通配符的主要作用就是让泛型能够接受未知类型的数据. 
<? extends T> 表示类型的上界，表示参数化类型的可能是T 或是 T的子类
<? super T> 表示类型下界（Java Core中叫超类型限定），
​	表示参数化类型是此类型的超类型（父类型），直至Object
注意： 你可以为一个泛型指定上边界或下边界, 但是不能同时指定上下边界.

####  泛型中K T V E ？ object等的含义

E - Element (在集合中使用，因为集合中存放的是元素)

T - Type（Java 类）

K - Key（键）

V - Value（值）

N - Number（数值类型）

？ - 表示不确定的java类型（无限制通配符类型）

S、U、V - 2nd、3rd、4th types

Object - 是所有类的根类，任何类的对象都可以设置给该Object引用变量，使用的时候可能需要类型强制转换，但是用使用了泛型T、E等这些标识符后，在实际用之前类型就已经确定了，不需要再进行类型强制转换。