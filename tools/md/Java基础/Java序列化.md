# Java序列化

##  1. 什么是序列化？
- Java序列化是指把Java对象转换为字节序列的过程；

- Java反序列化是指把字节序列恢复为Java对象的过程；

## 2.为啥要序列化？

- 一般Java对象的生命周期比Java虚拟机端，而实际开发中如果需要JVM停止后能够继续持有对象，则需要用到序列化技术将对象持久化到磁盘或数据库。
- 在多个项目进行RPC调用时，需要在网络上传输JavaBean对象，而网络上只允许二进制形式的数据进行传输，这时则需要用到序列化技术。

其实我理解的是：1. 跟数据库一样，作为服务的持久化数据 2. RPC传输对象必须序列化，比如dubbo

## 3. 序列号有什么用？

参见上面，为啥要序列化

## 4. 怎么序列化？

- 将对象实例相关的**类元数据**输出
- **递归**地输出类的超类描述，直到没有超类
- 类元数据输出之后，开始从最顶层的超类输出对象实例的**实际数据值**
- 从上至下**递归**输出实例的数据

所以，如果**父类已经序列化了，子类继承之后也可以进行序列化**。



实现第一步，则需要的先将对象实例相关的类标记为需要序列化。

```java
public class SerializeNY {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        serializeNY();
        NY ny = deserializeNY();
        System.out.println(ny.toString());
    }

    private static void serializeNY() throws IOException {
        NY ny = new NY();
        ny.setName("NY");
        ny.setBlogName("NYfor2020");
        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(new File("D:\\serialable.txt")));
        oos.writeObject(ny);
        System.out.println("NY 对象序列化成功！");
        oos.close();
    }

    private static NY deserializeNY() throws IOException, ClassNotFoundException {
        ObjectInputStream ois = new ObjectInputStream(new FileInputStream(new File("D:\\serialable.txt")));
        NY ny = (NY) ois.readObject();
        System.out.println("NY 对象反序列化成功");
        return ny;
    }
}
```

- 

## 5. 序列化有什么要注意的点吗？

### 1. transient 关键字

- 变量一旦被transient修饰，则不再是对象持久化的一部分了，而且变量内容在反序列化时也不能获得。
- transient关键字只能修饰变量，而不能修饰方法和类，而且本地变量是不能被transient修饰的，如果变量是类变量，则需要该类也实现Serializable接口。
- 一个静态变量不管是否被transient修饰，都不会被序列化。

### 2. transient真的不会被序列化吗？

```java

```

我们可以看到，content变量在被transient修饰的情况下，还是被序列化了。因为在Java中，对象序列化可以通过实现两种接口来实现：

- 如果实现的是**Serializable**接口，则所有信息（不包括被static、transient修饰的变量信息）的序列化将**自动**进行。
- 如果实现的是**Externalizable**接口，则不会进行自动序列化，需要开发者在**writeExternal()方法中手工**指定需要序列化的变量，与是否被transient修饰无关。

### 3. 总结

- 序列化对象必须实现序列化接口Serializable。

- 序列化对象中的属性如果也有对象的话，其对象需要实现序列化接口。

- 类的对象序列化后，类的序列号不能轻易更改，否则反序列化会失败。

- 类的对象序列化后，类的属性增加或删除不会影响序列化，只是值会丢失。

- 如果父类序列化，子类会继承父类的序列化；如果父类没序列化，子类序列化了，子类中的属性能正常序列化，但父类的属性会丢失，不能序列化。

- 用Java序列化的二进制字节数据只能由Java反序列化，如果要转换成其他语言反序列化，则需要先转换成Json/XML通用格式的数据。

- 如果某个字段不想序列化，在该字段前加上transient关键字即可。

## 序列化如何破坏单例，以及如何阻止序列化破坏单例
请查看多线程里面的单例模式，有具体介绍