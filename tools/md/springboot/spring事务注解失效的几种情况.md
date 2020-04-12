# 事务注解失效

## 1. @Transactional 应用在非 public 修饰的方法上

1. 之所以会失效是因为在Spring AOP 代理时， `TransactionInterceptor` （事务拦截器）在目标方法执行前后进行拦截，`DynamicAdvisedInterceptor`（CglibAopProxy 的内部类）的 intercept 方法或 `JdkDynamicAopProxy` 的 invoke 方法会间接调用 `AbstractFallbackTransactionAttributeSource`的 `computeTransactionAttribute` 方法，获取Transactional 注解的事务配置信息。

2. 此方法会检查目标方法的修饰符是否为 public，不是 public则不会获取@Transactional 的属性配置信息。

注意：

 **protected、private 修饰的方法上使用 @Transactional 注解，虽然事务无效，但不会有任何报错，这是我们很容犯错的一点。**



## 2. 事务的传播行为设置错误

这种失效是由于配置错误，若是错误的配置以下三种 propagation，事务将不会发生回滚。

`TransactionDefinition.PROPAGATION_SUPPORTS`：如果当前存在事务，则加入该事务；如果当前没有事务，则以非事务的方式继续运行。
`TransactionDefinition.PROPAGATION_NOT_SUPPORTED`：以非事务方式运行，如果当前存在事务，则把当前事务挂起。
`TransactionDefinition.PROPAGATION_NEVER`：以非事务方式运行，如果当前存在事务，则抛出异常。



## 3. 事务的rollbackFor设置错误


`rollbackFor` 可以指定能够触发事务回滚的异常类型。Spring默认抛出了未检查`unchecked`异常（继承自 `RuntimeException`的异常）或者 `Error`才回滚事务；其他异常不会触发回滚事务。如果在事务中抛出其他类型的异常，但却期望 Spring 能够回滚事务，就需要指定 **rollbackFor**属性

- 所以io相关的异常是不会回滚的

- 希望自定义的异常可以进行回滚

  ```java
   @Transactional(propagation= Propagation.REQUIRED,rollbackFor= MyException.class
                 
  ```

- 若在目标方法中抛出的异常是 `rollbackFor` 指定的异常的子类，事务同样会回滚。

## 4. 同一个类中方法调用，导致@Transactional失效

开发中避免不了会对同一个类里面的方法调用，比如有一个类Test，它的一个方法A，A再调用本类的方法B（不论方法B是用public还是private修饰），但方法A没有声明注解事务，而B方法有。则外部调用方法A之后，方法B的事务是不会起作用的。这也是经常犯错误的一个地方。

那为啥会出现这种情况？其实这还是由于使用`Spring AOP`代理造成的，因为只有当事务方法被当前类以外的代码调用时，才会由`Spring`生成的代理对象来管理。

但是注意的是：并不是两个方法都失去了事务，而只是子方法失去了自己原本的事务，而大方法如果本来有事务还是具备的

## 5.捕获异常不当导致注解失效（非常重要）

```java
 1    @Transactional
 2    private Integer A() throws Exception {
 3        int insert = 0;
 4        try {
 5            CityInfoDict cityInfoDict = new CityInfoDict();
 6            cityInfoDict.setCityName("2");
 7            cityInfoDict.setParentCityId(2);
 8            /**
 9             * A 插入字段为 2的数据
10             */
11            insert = cityInfoDictMapper.insert(cityInfoDict);
12            /**
13             * B 插入字段为 3的数据
14             */
15            b.insertB();
16        } catch (Exception e) {
17            e.printStackTrace();
18        }
19    }
```

如果B方法内部抛了异常，而A方法此时try catch了B方法的异常，那这个事务还能正常回滚吗？

答案：不能！

会抛出异常：`org.springframework.transaction.UnexpectedRollbackException: Transaction rolled back because it has been marked as rollback-only`

因为当`ServiceB`中抛出了一个异常以后，`ServiceB`标识当前事务需要`rollback`。但是`ServiceA`中由于你手动的捕获这个异常并进行处理，`ServiceA`认为当前事务应该正常`commit`。此时就出现了前后不一致，也就是因为这样，抛出了前面的`UnexpectedRollbackException`异常。

`spring`的事务是在调用业务方法之前开始的，业务方法执行完毕之后才执行`commit` or `rollback`，事务是否执行取决于是否抛出`runtime异常`。如果抛出`runtime exception` 并在你的业务方法中没有catch到的话，事务会回滚。

在业务方法中一般不需要catch异常，如果非要catch一定要抛出`throw new RuntimeException()`，或者注解中指定抛异常类型`@Transactional(rollbackFor=Exception.class)`，否则会导致事务失效，数据commit造成数据不一致，所以有些时候try catch反倒会画蛇添足。



## 6、数据库引擎不支持事务

这种情况出现的概率并不高，事务能否生效数据库引擎是否支持事务是关键。常用的MySQL数据库默认使用支持事务的`innodb`引擎。一旦数据库引擎切换成不支持事务的`myisam`，那事务就从根本上失效了。