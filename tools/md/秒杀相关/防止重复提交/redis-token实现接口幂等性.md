[自己以前的博客介绍](https://blog.csdn.net/qq_39455116/article/details/99314489)

主要是redis + token机制实现接口幂等性校验（实现是：通过拦截器+注解, 就不用每次请求都写重复代码, 其实也可以利用spring aop实现, 无所谓）

# 接口幂等性



1. 唯一索引 -- 防止新增脏数据
2. token机制 -- 防止页面重复提交
3. 悲观锁 -- 获取数据的时候加锁(锁表或锁行)
4. 乐观锁 -- 基于版本号version实现, 在更新数据那一刻校验数据
5. 分布式锁 -- redis(jedis、redisson)或zookeeper实现



