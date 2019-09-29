
# 1. 本系统构建环境

| 技术选择               | 列表             | 依赖信息                                                     | 主要作用                                                     | 官网                                                 |
| :--------------------- | ---------------- | ------------------------------------------------------------ | ------------------------------------------------------------ | ---------------------------------------------------- |
| RPC框架                | dubbo            | com.alibaba.boo<br />dubbo-spring-boot-starte<br />0.2.0 | RPC跨服务传输                                                |          [dubbo](https://github.com/alibaba/dubbo-spring-boot-starter)                                            |
| spring框架             | springboot2.x    | 2.1.5.RELEASE|                                                              |                                                      |
| 权限框架               | Shiro   | 1.4.1                       | 1.   shiro主要用来登录、授权等管理.<br />2. shiro整合redis来缓存权限等信息<br /> |[shiro](http://shiro.apache.org/)|
| 登录验证 | JWT | 3.8.2 | 1. jwt验证是否登录、登录过期检测<br/>2. 多服务共享登录信息 |[JWT](https://github.com/auth0/java-jwt)|
| 注册中心               | zookeper集群     |                                                              | 1. 注册dubbo服务                                             |                                                      |
| 全局ID                 | 分布式雪花ID     |                                                              | 2. 提供所有主键ID                                            |   [SnowFlake](https://github.com/souyunku/SnowFlake)                                                   |
| 数据库 | mysql | 8.0.14                               | 主从复制 |  |
| 数据库监控 | druid | 1.1.18 | 数据监控 | [druid](https://github.com/alibaba/druid) |
| 数据库持久层框架       | MybatisPlus      |              3.1.2                                                | 1. 分页<br/>2.逻辑删除 <br/>3. 乐观锁配置<br/>4. druid动态数据源<br/>5. 读写分离<br/>6. 分库分表，一库多表                    |                         [mybatisPlus](https://mp.baomidou.com/guide/)                             |
|           JSON             |         Fastjson         |                 1.2.59                                             |    1. 接口返回<br/>2.redis缓存序列化工具|       [fastjson](https://github.com/alibaba/fastjson)                                        |
| ... |                  |                                                              |                                                              |                                                      |



## 1.1  分布式相关

| 技术相关        | 依赖                                       | 主要作用                | 官网                                                         |
| --------------- | ------------------------------------------ | ----------------------- | ------------------------------------------------------------ |
| redis/redis集群 | spring-boot-starter-data-redis<br />2.1.14 | 1.Redis的分布式锁<br /> |                                                              |
| Redisson        | 3.11.2                                     | 1. 分布式锁             | [redisson](https://github.com/redisson/redisson#quick-start) |
| RabbitMQ        |                                            |                         |                                                              |
| ...             |                                            |                         |                                                              |



# 2. 多模块dubbo架构

```java
├─common-api         ## 公共API
├─common-consumer    ## 公共消费者
├─common-provider    ## 公共提供者
├─common-tools       ## 自动生成代码工具
├─common-user 
├──────common-api          ## 用户API
├──────common-consumer     ## 用户消费者
├──────common-provider     ## 用户提供者
```



## 2.1 公共模块common
1. 放入全局redis存入数据的配置，用jackjson序列化数据，但是不涉及查询的缓存，查询的缓存我们考虑用fastjson，当然我们需要改一下@Bean的name，使用的时候要注入哪个自己定义

```
redisTemplateFastJson
redisTemplateJackJson
```

2. 
暂时还没有想好以后还要放什么，未来可能会放入一个美团分布式ID系统吧

**美团分布式ID系统两种实现，详细分析如下**

地址：[博客地址](https://blog.csdn.net/qq_39455116/article/details/99458001)
1. 号段模式
2. snowflake模式

## 2.2 用户模块
1. 用户模块使用JWT+shiro实现伪单点登录系统，
2. 为什么说是伪单点登录，因为正常的单点登录是A系统登录了，BCDE等系统就不需要登录了，而且BCDE等系统有单独的controller
3. 但是我们现在的伪分布式系统，只用一个消费者A，4个提供者BCDE，是要做成一套系统的，不涉及多个系统，所以就出现了这样一个问题:BCDE等系统不需要权限就能访问，这会出现很严重的问题，权限失控问题！
4. 对此我的解决方案很简单粗暴，对于BCDE...等提供服务的，不提供控制层controller，只提供API和接口，这也是我们用dubbo的一个主要的原因，跨服务RPC
### 2.2.1 shiro登录等权限验证
步骤：

1. 用户登录返回JWT认证信息,如下面的返回格式，当然你可以自己定义
```json
{
    "msg": "Login success",
    "code": "200",
    "data": "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJleHAiOjE1NjY4MDg4MDMsInVzZXJuYW1lIjoieGl5b3UifQ.sf7gPytPTDy7sm71VSjYHI-00JnUFQF0zR3FqrGuv-4",
    "count": 0
}
```
2. 以后前端把JWT的认证信息，即data里面很长的字符串放到下次请求接口里面的header里面，如下：
![授权](https://img-blog.csdnimg.cn/20190826164858369.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzM5NDU1MTE2,size_16,color_FFFFFF,t_70)
3. 然后你可以在接口里面用注解方式写访问需要的权限，详细分析地址：**[注解介绍地址](https://www.cnblogs.com/pingxin/p/p00115.html)** 
示例如下：

```java
@ApiOperation(value = "测试需要用户认证的接口2", notes = "登录")
@GetMapping("/require_auth")
@RequiresAuthentication
public ResultInfo requireAuth() {
    return new ResultInfo("200", "You are authenticated", null);
}

@ApiOperation(value = "测试需要希尤角色的接口", notes = "登录")
@GetMapping("/require_role_xiyou")
@RequiresRoles("xiyou")
public ResultInfo requireRole() {
    return new ResultInfo("200", "You are visiting require_role", null);
}

@ApiOperation(value = "测试需要system权限的接口", notes = "登录")
@GetMapping("/require_permission_system")
@RequiresPermissions("system")
public ResultInfo requirePermissionS() {
    return new ResultInfo("200", "You are visiting require_role", null);
}
```


### 2.2.2 shiro整合redis，实现权限验证使用redis缓存
 如果接口中有需要权限认证的接口，系统中用fastjson+redis把权限的信息缓存到了redis，具体如何用fastjson把信息缓存到redis中，请参考：**[fastjson序列化redis](https://blog.csdn.net/qq_39455116/article/details/84400852)**
### 2.2.3 JWT 登录流程

1. JWT验证是否登录，登录失效等功能
2. JWT认证信息缓存到Redis中
3. 签发的用户认证token超时刷新策略
4. 用户退出登录删除redis里面的认证信息

 参考 ：**[签发的用户认证token超时刷新策略](https://segmentfault.com/a/1190000014545422)**
参考：**[github](https://github.com/tomsun28/bootshiro)**


![登录](https://imgconvert.csdnimg.cn/aHR0cHM6Ly9pbWFnZS1zdGF0aWMuc2VnbWVudGZhdWx0LmNvbS8yNzAvMzM3LzI3MDMzNzA4ODItNWFkZDk1NWY4YzE1Mw?x-oss-process=image/format,png)    


## 2.3 tools模块
### 2.3.1 MyBatisPlus3.x自动生成代码工具
主要生成entity mapper service controller


# 3  全局统一配置信息
## 4.1 端口配置
8801开始都是提供者
8851开始都是消费者
其中8801的提供者对应的消费者是8851，同时dubbo的端口对应的也是dubbo.protocol.port=20881

同理8802-------8852 dubbo.protocol.port=20882

## 4.2 超时时间等

# 4. MySql设计


 - **主键**
1. 所有的主键都采用雪花ID，或者基于雪花ID前面加入表的前缀或者雪花ID后面加入表的后缀，又或者自定义，如：
    362312004687638590
    sys_user_362312004687638590
    362312004687638590_sys_user
    362312004_sys_user_87638590
    如上多种自定义方式，只要利于查询的主键即可，当然主键没有默认值，因为主键不能重复
2. 后期考虑美团的分布式ID
 - **字符类型**
1. 所有数据库表的设计都不允许为NULL，String类型包括，char  varchar  text 默认的都是： DefaultValue

 - **单列唯一索引**
1. 字段不允许重复的比如用户名，手机号等，除了唯一索引要求，唯一索引默认值也是：DefaultValue
 - 5.4 TinyInt
2. TinyInt 默认的是：0
 -  **其它类型**
3. datetime 类型默认都是当前时间： CURRENT_TIMESTAMP
4. int 类型默认的是：0，当然我们表中主键都是雪花ID，很少涉及int，如果有涉及，默认是：0
5. 小数的数据精确到后面4位，当然不存粗小数数字，如果有小数，乘以10000之后再存储，展示的时候可以除以1万，或者Java层面解决
6. 如果有特殊情况，建议用bigDecimal或者字符串


## 5. 整合ES
文章地址：https://blog.csdn.net/qq_39455116/article/details/101692511
