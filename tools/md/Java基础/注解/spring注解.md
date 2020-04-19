## 注解属性

@Documented –注解是否将包含在JavaDoc中

@Retention –什么时候使用该注解

@Target? –注解用于什么地方

@Inherited – 是否允许子类继承该注解

## 注解详细介绍

@Documented–一个简单的Annotations标记注解，表示是否将注解信息添加在java文档中。

@Retention– 定义该注解的生命周期。

RetentionPolicy.SOURCE – 在编译阶段丢弃。这些注解在编译结束之后就不再有任何意义，所以它们不会写入字节码。@Override, @SuppressWarnings都属于这类注解。

RetentionPolicy.CLASS – 在类加载的时候丢弃。在字节码文件的处理中有用。注解默认使用这种方式。

RetentionPolicy.RUNTIME– 始终不会丢弃，运行期也保留该注解，因此可以使用反射机制读取该注解的信息。我们自定义的注解通常使用这种方式。

@Target – 表示该注解用于什么地方。如果不明确指出，该注解可以放在任何地方。以下是一些可用的参数。需要说明的是：属性的注解是兼容的，如果你想给7个属性都添加注解，仅仅排除一个属性，那么你需要在定义target包含所有的属性。

ElementType.TYPE:用于描述类、接口或enum声明

ElementType.FIELD:用于描述实例变量

ElementType.METHOD

ElementType.PARAMETER

ElementType.CONSTRUCTOR

ElementType.LOCAL_VARIABLE

ElementType.ANNOTATION_TYPE 另一个注释

ElementType.PACKAGE 用于记录java文件的package信息

@Inherited – 定义该注释和子类的关系

那么，注解的内部到底是如何定义的呢？Annotations只支持基本类型、String及枚举类型。注释中所有的属性被定义成方法，并允许提供默认值。

### 自定义注解

开发中日常使用注解大部分是用在类上，方法上，字段上，示列代码如下：

```java
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface EnableAuth {

}

```

### spring常用注解

@Configuration把一个类作为一个IoC容器，它的某个方法头上如果注册了@Bean，就会作为这个Spring容器中的Bean。

@Scope注解 作用域

@Lazy(true) 表示延迟初始化

@Service用于标注业务层组件、

@Controller用于标注控制层组件@Repository用于标注数据访问组件，即DAO组件。

@Component泛指组件，当组件不好归类的时候，我们可以使用这个注解进行标注。

@Scope用于指定scope作用域的（用在类上）

@PostConstruct用于指定初始化方法（用在方法上）

@PreDestory用于指定销毁方法（用在方法上）

@DependsOn：定义Bean初始化及销毁时的顺序

@Primary：自动装配时当出现多个Bean候选者时，被注解为@Primary的Bean将作为首选者，否则将抛出异常

@Autowired 默认按类型装配，如果我们想使用按名称装配，可以结合@Qualifier注解一起使用。如下：

@Autowired @Qualifier("personDaoBean") 存在多个实例配合使用

@Resource默认按名称装配，当找不到与名称匹配的bean才会按类型装配。

@PostConstruct 初始化注解

@PreDestroy 摧毁注解 默认 单例 启动就加载



###  Spring中的这几个注解有什么区别
@Component 、@Repository、@Service、 controller

1. @Component指的是组件，

@Controller，@Repository和@Service 注解都被@Component修饰，用于代码中区分表现层，持久层和业务层的组件，代码中组件不好归类时可以使用@Component来标注

比如controller注解

```java
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Component
public @interface Controller {
    @AliasFor(
        annotation = Component.class
    )
    String value() default "";
}
```

### 注解与反射与AOP

#### 自定义注解+token防止接口重复提交

秒杀相关、防止重复提交里面

#### 自定义注解+令牌桶算法+aop实现通用接口限流

目录秒杀相关、限流里面

