## spring事务

事务的本质

1. 获取连接 Connection con = DriverManager.getConnection()
2. 开启事务con.setAutoCommit(true/false);
3. 执行CRUD
4. 提交事务/回滚事务 con.commit() / con.rollback();
5. 关闭连接 conn.close();

## 1. @Transactional注解

船惹可信Nou

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

  - 即只要大方法里面有事务，小方法即使没有事务，也会用大事务的
    说明都在沿用当前存在的事务，
  - 如果大方法有事务，但是小方法也有，它的意思是我承认小方法你有自己的事务
  - 如果大方法没有事务，调用的小方法里面有，则小方法自己的事务自己会回滚，但是不影响大方法的执行，也就是大方法没有事务
  - 总结：就是它承认它内部的方法有用事务，如果内部的方法有自己的事务，则内部的方法就用自己内部的事务。如果内部的方法没有事务，则沿用当前事务。如果外部的方法没有事务，则不管内部的方法有没有事务，外部的方法都不会有事务。也就是它只承认内部的，但是不承认外部的

- `Propagation.SUPPORTS`：如果当前存在事务，则加入该事务；如果当前不存在事务，则以非事务的方式继续运行。

  - 假设ServiceB.methodB() 的事务级别为 PROPAGATION_SUPPORTS，那么当执行到ServiceB.methodB()时，如果发现ServiceA.methodA()已经开启了一个事务，则加入当前的事务，如果发现ServiceA.methodA()没有开启事务，则自己也不开启事务。这种时候，内部方法的事务性完全依赖于最外层的事务。

- `Propagation.MANDATORY`：如果当前存在事务，则加入该事务；如果当前不存在事务，则抛出异常。

- `Propagation.REQUIRES_NEW`：重新创建一个新的事务，如果当前存在事务，暂停当前的事务。**(** 当类A中的 a 方法用默认`Propagation.REQUIRED`模式，类B中的 b方法加上采用 `Propagation.REQUIRES_NEW`模式，然后在 a 方法中调用 b方法操作数据库，然而 a方法抛出异常后，b方法并没有进行回滚，因为`Propagation.REQUIRES_NEW`会暂停 a方法的事务 **)**

  - 用于子方法
  - 无论当前事务是否存在，都会创建新事物运行方法
    这样新事务就会拥有新的锁和新的事务隔离级别，与当前事务互相独立
  - 新建事务，如果当前存在事务，把当前事务挂起。新建的事务将和被挂起的事务没有任何关系，是两个独立的事务，外层事务失败回滚之后，不能回滚内层事务执行的结果，内层事务失败抛出异常，外层事务捕获，也可以不处理回滚操作

- `Propagation.NOT_SUPPORTED`：以非事务的方式运行，如果当前存在事务，暂停当前的事务。

- `Propagation.NEVER`：以非事务的方式运行，如果当前存在事务，则抛出异常。

- `Propagation.NESTED` ：和 Propagation.REQUIRED 效果一样。

  - 但是NESTED一般用于子方法，所以又不太一样

  - 在当前方法调用子方法时，如果子方法发生异常，并且被捕获的情况下
    只回滚子方法执行的SQL，而不回滚当前方法执行的事务



```java
比如我们设计 ServiceA.methodA() 的事务级别为 PROPAGATION_REQUIRED，ServiceB.methodB() 的事务级别为 PROPAGATION_REQUIRES_NEW。

那么当执行到 ServiceB.methodB() 的时候，ServiceA.methodA() 所在的事务就会挂起，ServiceB.methodB() 会起一个新的事务，等待 ServiceB.methodB() 的事务完成以后，它才继续执行。

他与 PROPAGATION_REQUIRED 的事务区别在于事务的回滚程度了。因为 ServiceB.methodB() 是新起一个事务，那么就是存在两个不同的事务。如果 ServiceB.methodB() 已经提交，那么 ServiceA.methodA() 失败回滚，ServiceB.methodB() 是不会回滚的。如果 ServiceB.methodB() 失败回滚，如果他抛出的异常被 ServiceA.methodA() 捕获，ServiceA.methodA() 事务仍然可能提交(主要看B抛出的异常是不是A会回滚的异常)。
```



### 1.3 timeout

`timeout` ：事务的超时时间，默认值为 -1。如果超过该时间限制但事务还没有完成，则自动回滚事务。

### 1.4  readOnly 属性

`readOnly` ：指定事务是否为只读事务，默认值为 false；为了忽略那些不需要事务的方法，比如读取数据，可以设置 read-only 为 true。

从这一点设置的时间点开始（时间点a）到这个事务结束的过程中，其他事务所提交的数据，该事务将看不见！（查询中不会出现别人在时间点a之后提交的数据）。

@Transcational(readOnly=true) 这个注解一般会写在业务类上，或者其方法上，用来对其添加事务控制。当括号中添加readOnly=true, 则会告诉底层数据源，这个是一个只读事务，对于JDBC而言，只读事务会有一定的速度优化。

而这样写的话，事务控制的其他配置则采用默认值，事务的隔离级别(isolation) 为DEFAULT,也就是跟随底层数据源的隔离级别，事务的传播行为(propagation)则是REQUIRED，所以还是会有事务存在，一代在代码中抛出RuntimeException，依然会导致事务回滚。

 应用场合：

- 如果你一次执行单条查询语句，则没有必要启用事务支持，数据库默认支持SQL执行期间的读一致性；
- 如果你一次执行多条查询语句，例如统计查询，报表查询，在这种场景下，多条查询SQL必须保证整体的读一致性，否则，在前条SQL查询之后，后条SQL查询之前，数据被其他用户改变，则该次整体的统计查询将会出现读数据不一致的状态，此时，应该启用事务支持。

【注意是一次执行多次查询来统计某些信息，这时为了保证数据整体的一致性，要用只读事务】

### 4.5 rollbackFor 属性
rollbackFor ：用于指定能够触发事务回滚的异常类型，可以指定多个异常类型。

### 4.6 noRollbackFor属性
noRollbackFor：抛出指定的异常类型，不回滚事务，也可以指定多个异常类型。