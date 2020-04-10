# springIOC

inversion of control  控制反转（尹我森 ）

## 1. 依赖倒置思想

### 牵一发而动全身的设计

比如我们要设计一款车，刚开始设计螺丝，根据螺丝设计轮子，再根据轮子设计地盘，然后组成车

这样往往牵一发而动全身，为啥？因为如果我想改车的轮子了，我发现轮子改了车地盘就得改，地盘改了车就要改这完全废了啊

### 顶层建筑决定底层实现

上面的思路不行，所以我们反过来，先设计车，再设计地盘，再根据车地盘设计轮子，再根据轮子设计螺丝，这样如果我们要改车的轮子了，我们只需要动车的轮子，而不需要动车地盘

**高层建筑决定需要什么，底层去实现这样的需求，但是高层并不用管底层是怎么实现的。**这样就不会出现前面的“牵一发动全身”的情况。



以前看过一篇用外卖来讲解spring控制反转的帖子，感觉也挺好理解的。外卖没出来的时候，我们需要自己找到想去的店，然后再一个个的点菜，后面有外卖平台后（也就相当于spring），我们只需要注册外卖平台（由spring统一管理），然后选我们对应想点的菜（告诉spring我需要什么），就OK了，说得有点糙

## 2. 控制反转实现是依赖注入DI

其实依赖倒置原则的一种代码设计的思路。具体采用的方法就是所谓的依赖注入（Dependency Injection）

（d pen den say   尹jack 信）

即上层控制下层，而不是下层控制着上层。我们用依赖注入（Dependency Injection）这种方式来实现控制反转。所谓依赖注入，就是把底层类作为参数传入上层类，实现上层类对下层类的“控制”。



### 2.1 控制反转容器IOC

ioc Container

因为采用了依赖注入，在初始化的过程中就不可避免的会写大量的new。这里IoC容器就解决了这个问题。这个容器可以自动对你的代码进行初始化，你只需要维护一个Configuration（可以是xml可以是一段代码），而不用每次初始化一辆车都要亲手去写那一大段初始化的代码。

这是引入IoC Container的第一个好处。IoC Container的第二个好处是：我们在创建实例的时候不需要了解其中的细节。在上面的例子中，我们自己手动创建一个车instance时候，是从底层往上层new的：





springIOC 是管理bean的容器，默认情况下bean都是以单例存在的,它要求所有ioc容器都要去实现BeanFactory接口，而BeanFactory接口中有根据bean名称或者类型返回bean的方法，这也为依赖注入DI提供了可能

### 2.2 依赖注入DI 的方式

#### @Bean+@Configration实现注入

@Configration代表这是一个配置文件

这种方式比较复杂

####  @ComponentScan或者@Component实现注入

@ComponentScan：是以何种策略扫描装配bean

@Component是标记当前类进入IOC容器

比如我们常见的@Service注解，内部实现就是用@Component

```
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Component
public @interface Service {}
```

### 2.3自定义第三方Bean

比如我们常见的MYSQL或者mongo等数据库，我们常常会自己定义一个bean引入进来，当然不自己定义也可以，因为springboot默认实现了，但是如果比如我们开发多数据源的时候就要用到了



### 2.4@Autowired注解获取bean

根据类型获取bean

@Primary作用是如果发现有多个同样类型的bean时，优先选择哪个



# bean 生命周期

1. 通过我们的配置@ComponentScan定义去扫描带有@Component的类
2. 找到这些类之后，开始解析，并且把定义的信息保存起来，但是没有进行bean初始化，也没有bean的实例，仅仅是bean的定义

将所有的 Bean 定义保存到 BeanDefinition 的实例中 

1. 发布bean到IOC容器中，此时IOC里面也仅仅只有Bean的定义，还是没有bean的实例
2. 实例化，创建bean的实例对象
3. 依赖注入DI（例如@Autowired）



