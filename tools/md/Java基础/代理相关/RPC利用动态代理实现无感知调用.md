## 动态代理多种方式

- JDK

- CGLib

  - spring 默认的是JDK+CGlib

- javassist

  它是通过**直接操作字节码**来**生成代理对象**,同时要有javassist依赖

  - dubbo默认的是Javassist因为javassist更快，执行多次就可以显著提升性能
  - dubbo 没有**预留出动态代理**扩展接口**，**写死了 bytecode


```java
    public <T> T getProxy(Invoker<T> invoker, Class<?>[] interfaces) {
        return (T) Proxy.getProxy(interfaces).newInstance(new InvokerInvocationHandler(invoker));
    }
```

getProxy得到新的(字节码动态生成的)Proxy类的实例后，通过生成的Proxy覆盖的newInstance方法，得到新的proxyInstance(也是字节码动态生成的)实例。我们也可以从字节码角度看到，生成的代理，在消费者角度，看起来调用了消费者端的方法，实际上调用了代理的相应方法，最后直接调用handler的方法，达到代理目的。



- ASM

## dubbo
- dubbo 默认是javassist 但是也可以采取JDK动态代理