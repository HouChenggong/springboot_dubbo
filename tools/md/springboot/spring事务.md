## spring事务

## 1. @Transactional注解

注解的源码：

```java
public @interface Transactional {
    @AliasFor("transactionManager")
    String value() default "";

    @AliasFor("value")
    String transactionManager() default "";

    Propagation propagation() default Propagation.REQUIRED;

    Isolation isolation() default Isolation.DEFAULT;

    int timeout() default -1;

    boolean readOnly() default false;

    Class<? extends Throwable>[] rollbackFor() default {};

    String[] rollbackForClassName() default {};

    Class<? extends Throwable>[] noRollbackFor() default {};

    String[] noRollbackForClassName() default {};
}
```

@Transactional 可以作用在`接口`、`类`、`类方法`。

- **作用于类**：当把@Transactional 注解放在类上时，表示所有该类的`public`方法都配置相同的事务属性信息。
- **作用于方法**：当类配置了@Transactional，方法也配置了@Transactional，方法的事务会覆盖类的事务配置信息。
- **作用于接口**：不推荐这种使用方法，因为一旦标注在Interface上并且配置了Spring AOP 使用CGLib动态代理，将会导致@Transactional注解失效

常用的都是在具体的serviceImpl里面添加事务，而且只有增删改需要，查询是不需要事务的

### 1.1.事务的隔离级别

推荐看一下MySQL的四种隔离级别

- 未提交读
- 提交都
- 可重复读
- 序列化

#### 隔离级别isolation 属性

`isolation` ：事务的隔离级别，默认值为 `Isolation.DEFAULT`。

- Isolation.DEFAULT：使用底层数据库默认的隔离级别。（MySQL默认的是可重复读）

- Isolation.READ_UNCOMMITTED

- Isolation.READ_COMMITTED

- Isolation.REPEATABLE_READ

- Isolation.SERIALIZABLE





#### 2.1 ACID

- 原子性：事务内的所有操作要么全部完成，要么全部回滚；

- 一致性：事务执行前后的状态都是合法的数据状态，不会违反任何的数据完整性；比如转账前后数据的总和是不变的

- 隔离性：主要是事务之间的相互的影响，根据隔离有不同的影响效果。

- 持久性：事务一旦提交，就会体现在数据库上，不能回滚

  | 事务特性          | MySQL                | mongo                                    | ...  |
  | ----------------- | -------------------- | ---------------------------------------- | ---- |
  | Atomicity原子性   | un do log(回滚)      | journal 机制来实现                       |      |
  | Consistency一致性 | redo log             | journal 机制来实现                       |      |
  | 隔离性Isolation   | 隔离级别+MVCC+读写锁 | 隔离级别 + 读写锁 + snapshot+ MVCC来实现 |      |
  | Durability持久性  |                      |                                          |      |
  |                   |                      |                                          |      |





### 1.2 事务的传播行为

`propagation` 代表事务的传播行为，默认值为 `Propagation.REQUIRED`，其他的属性信息如下：

- `Propagation.REQUIRED`：如果当前存在事务，则加入该事务，如果当前不存在事务，则创建一个新的事务。**(** 也就是说如果A方法和B方法都添加了注解，在默认传播模式下，A方法内部调用B方法，会把两个方法的事务合并为一个事务 **）**
- `Propagation.SUPPORTS`：如果当前存在事务，则加入该事务；如果当前不存在事务，则以非事务的方式继续运行。
- `Propagation.MANDATORY`：如果当前存在事务，则加入该事务；如果当前不存在事务，则抛出异常。
- `Propagation.REQUIRES_NEW`：重新创建一个新的事务，如果当前存在事务，暂停当前的事务。**(** 当类A中的 a 方法用默认`Propagation.REQUIRED`模式，类B中的 b方法加上采用 `Propagation.REQUIRES_NEW`模式，然后在 a 方法中调用 b方法操作数据库，然而 a方法抛出异常后，b方法并没有进行回滚，因为`Propagation.REQUIRES_NEW`会暂停 a方法的事务 **)**
- `Propagation.NOT_SUPPORTED`：以非事务的方式运行，如果当前存在事务，暂停当前的事务。
- `Propagation.NEVER`：以非事务的方式运行，如果当前存在事务，则抛出异常。
- `Propagation.NESTED` ：和 Propagation.REQUIRED 效果一样。

### 1.3 timeout

`timeout` ：事务的超时时间，默认值为 -1。如果超过该时间限制但事务还没有完成，则自动回滚事务。

### 1.4  readOnly 属性

`readOnly` ：指定事务是否为只读事务，默认值为 false；为了忽略那些不需要事务的方法，比如读取数据，可以设置 read-only 为 true。
### 4.5 rollbackFor 属性
rollbackFor ：用于指定能够触发事务回滚的异常类型，可以指定多个异常类型。

### 4.6 noRollbackFor属性
noRollbackFor：抛出指定的异常类型，不回滚事务，也可以指定多个异常类型。