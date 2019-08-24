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



# 2. 分布式相关

| 技术相关        | 依赖                                       | 主要作用                | 官网                                                         |
| --------------- | ------------------------------------------ | ----------------------- | ------------------------------------------------------------ |
| redis/redis集群 | spring-boot-starter-data-redis<br />2.1.14 | 1.Redis的分布式锁<br /> |                                                              |
| Redisson        | 3.11.2                                     | 1. 分布式锁             | [redisson](https://github.com/redisson/redisson#quick-start) |
| RabbitMQ        |                                            |                         |                                                              |
| ...             |                                            |                         |                                                              |



# 3. 多模块dubbo架构



## 3.1 公共模块common

暂时还没有想好放什么，先放入一个美团分布式ID系统吧

### 3.1.1 美团分布式ID系统两种实现

地址：[博客地址](https://blog.csdn.net/qq_39455116/article/details/99458001)
1. 号段模式
2. snowflake模式

## 3.2 用户模块

### 3.2.1 shiro登录等权限验证

### 3.2.2 shiro整合redis，实现权限验证使用redis缓存

### 3.2.3 JWT 共享多模块登录信息

1. JWT验证是否登录，登录失效等功能
2. JWT多模块共享登录信息
3. JWT实现登录失效处理

 参考 ：**[地址](https://segmentfault.com/a/1190000014545422)**
参考：**[github](https://github.com/tomsun28/bootshiro)**


![登录](https://imgconvert.csdnimg.cn/aHR0cHM6Ly9pbWFnZS1zdGF0aWMuc2VnbWVudGZhdWx0LmNvbS8yNzAvMzM3LzI3MDMzNzA4ODItNWFkZDk1NWY4YzE1Mw?x-oss-process=image/format,png)    


## 3.3 tools模块
### 3.3.1 MyBatisPlus3.x自动生成代码工具
主要生成entity mapper service controller


# 4  配置信息
## 4.1 端口配置
8801开始都是提供者
8851开始都是消费者
其中8801的提供者对应的消费者是8851，同时dubbo的端口对应的也是dubbo.protocol.port=20881

同理8802-------8852 dubbo.protocol.port=20882

# 5. MySql设计


 - 主键
1. 所有的主键都采用雪花ID，或者基于雪花ID前面加入表的前缀或者雪花ID后面加入表的后缀，又或者自定义，如：
    362312004687638590
    sys_user_362312004687638590
    362312004687638590_sys_user
    362312004_sys_user_87638590
    如上多种自定义方式，只要利于查询的主键即可，当然主键没有默认值，因为主键不能重复
2. 后期考虑美团的分布式ID
## 5.2 字符类型
1. 所有数据库表的设计都不允许为NULL，String类型包括，char  varchar  text 默认的都是： DefaultValue

## 5.3 单列唯一索引
1. 字段不允许重复的比如用户名，手机号等，除了唯一索引要求，唯一索引默认值也是：DefaultValue
## 5.4 TinyInt
2. TinyInt 默认的是：0
## 5.5  其它类型
3. datetime 类型默认都是当前时间： CURRENT_TIMESTAMP
4. int 类型默认的是：0，当然我们表中主键都是雪花ID，很少涉及int，如果有涉及，默认是：0
5. 小数的数据精确到后面4位，当然不存粗小数数字，如果有小数，乘以10000之后再存储，展示的时候可以除以1万，或者Java层面解决
6. 如果有特殊情况，建议用bigDecimal或者字符串
```

```
