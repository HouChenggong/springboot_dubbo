# 限流算法

## 1. 漏桶算法

漏桶算法思路很简单，水（请求）先进入到漏桶里，漏桶以一定的速度出水，当水流入速度过大会直接溢出，可以看出漏桶算法能强行限制数据的传输速率

## 2. 令牌桶算法

漏桶算法能够强行限制数据的传输速率，而令牌桶算法在能够限制数据的平均传输速率外，**还允许某种程度的突发传输**。在令牌桶算法中，只要令牌桶中存在令牌，那么就允许突发地传输数据直到达到用户配置的门限，**因此它适合于具有突发特性的流量**。

### 1. 非阻塞式

非阻塞式获取令牌：请求进来后，若令牌桶里没有足够的令牌，会尝试等待设置好的时间（这里写了1000ms），其会自动判断在1000ms后，这个请求能不能拿到令牌，如果不能拿到，直接返回抢购失败。如果timeout设置为0，则等于阻塞时获取令牌。

```java
rateLimiter.tryAcquire(1000, TimeUnit.MILLISECONDS)
```



### 2. 阻塞式

阻塞式获取令牌：请求进来后，若令牌桶里没有足够的令牌，就在这里阻塞住，等待令牌的发放。

```java
rateLimiter.acquire()
```

# 秒杀里面的限流和抢购如何结合？

### 1. 乐观锁

如果采用乐观锁更新，普通的更新如下，

- 普通CAS更新，但是会出现ABA问题，详情请查看CAS讲解

```sql
update table set value = newValue where  and id =XXX and value = #{oldValue}
//oldValue就是我们执行前查询出来的值
```

- 带版本号的更新方式，但是更新成功率会很低

```sql
update table set value = newValue ，vision = vision + 1 where id =XXX and
value = #{oldValue} and vision = #{vision} 
// 判断原来的值和版本号是否匹配，中间有别的线程修改，值可能相等，但是版本号100%不一样
```

- 针对秒杀的可以进一步优化：
  - 因为秒杀系统，库存都是依次递减的，所以不需要有版本号标识

```sql
update table set value = oldValue -1 where  and id =XXX and value = #{oldValue}
```

#### 乐观锁和限流的结合

- controller

```java
    @RequestMapping("/createOptimisticOrder/{sid}")
    @ResponseBody
    public String createOptimisticOrder(@PathVariable int sid) {
        // 阻塞式获取令牌
        //LOGGER.info("等待时间" + rateLimiter.acquire());
        // 非阻塞式获取令牌
        if (!rateLimiter.tryAcquire(1000, TimeUnit.MILLISECONDS)) {
            LOGGER.warn("你被限流了，真不幸，直接返回失败");
            return "购买失败，库存不足";
        }
        int id;
        try {
            id = orderService.createOptimisticOrder(sid);
            LOGGER.info("购买成功，剩余库存为: [{}]", id);
        } catch (Exception e) {
            LOGGER.error("购买失败：[{}]", e.getMessage());
            return "购买失败，库存不足";
        }
        return String.format("购买成功，剩余库存为：%d", id);
    }
```

- service
  - 当前service没有加事务，而且里面的子方法（减少库存、创建订单）也没有加事务

```java

    @Override
    public int createOptimisticOrder(int sid) {
        //校验库存
        Stock stock = checkStock(sid);
        //乐观锁更新库存
        saleStockOptimistic(stock);
        //创建订单
        int id = createOrder(stock);
        return stock.getCount() - (stock.getSale()+1);
    }
```

- 核心乐观锁更新代码

```sql
  <update id="updateByOptimistic" parameterType="cn.monitor4all.miaoshadao.dao.Stock">
    update stock
    <set>
      sale = sale + 1,
    </set>
    WHERE id = #{id,jdbcType=INTEGER}
    AND sale = #{sale,jdbcType=INTEGER}
  </update>
```



所以我们用乐观锁的时候，如果配合限流，可以不用事务，但是真实的场景是秒杀成功之后要下单，要发短信或者邮件等一些列操作，所以还是要有事务的，鉴于现在的下单比较简单，所以没有用事务

- 问题：上述的乐观锁更新代码的Mapper层面**updateByOptimistic**这个会有很大问题，就是假如有100个人都校验库存有剩余，然后剩余库存都是50个，然后这50个人用**updateByOptimistic**乐观锁更新，但是最后只有一个人会成功，所以成功率极低，那么你就会说能不能用下面的判断条件

```sql
    WHERE id = #{id,jdbcType=INTEGER}
    AND sale >0
```

可以用，但是用了就不是MVCC乐观锁了

### 2. 悲观锁

- controller

```java
    /**
     * 事务for update更新库存
     * @param sid
     * @return
     */
    @RequestMapping("/createPessimisticOrder/{sid}")
    @ResponseBody
    public String createPessimisticOrder(@PathVariable int sid) {
        int id;
        try {
            id = orderService.createPessimisticOrder(sid);
            LOGGER.info("购买成功，剩余库存为: [{}]", id);
        } catch (Exception e) {
            LOGGER.error("购买失败：[{}]", e.getMessage());
            return "购买失败，库存不足";
        }
        return String.format("购买成功，剩余库存为：%d", id);
    }
```

- service
  - service方法添加事务，如果遇到回滚，则返回Exception，并且事务传播使用`PROPAGATION_REQUIRED–支持当前事务，如果当前没有事务，就新建一个事务`

```java
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    @Override
    public int createPessimisticOrder(int sid){
        //校验库存(悲观锁for update)
        Stock stock = checkStockForUpdate(sid);
        //更新库存
        saleStock(stock);
        //创建订单
        int id = createOrder(stock);
        return stock.getCount() - (stock.getSale());
    }
```

- 悲观锁在大量请求的请求下，有着更好的卖出成功率。

- 核心是：`Stock selectByPrimaryKeyForUpdate(Integer id);` for update锁住当前记录

  但是需要注意的是，如果请求量巨大，悲观锁会导致后面的请求进行了长时间的阻塞等待，用户就必须在页面等待，很像是“假死”，可以通过配合令牌桶限流（参考乐观锁），或者是给用户显著的等待提示来优化。



## 感谢

[github项目地址](https://github.com/qqxx6661/miaosha/tree/master/miaosha-dao)

[微信公众号讲解](https://mp.weixin.qq.com/s/i5QLxas3h8XauA3HvW9f8w)

```java
https://github.com/qqxx6661/miaosha/tree/master/miaosha-dao
https://mp.weixin.qq.com/s/i5QLxas3h8XauA3HvW9f8w
```

## 额外

此外令牌桶限流算法，其实上述有些麻烦，如果想在N个接口都是用推荐用

- [AOP+注解+令牌桶实现指定接口限流](https://mp.weixin.qq.com/s/vyQZvJm5sjnEnrpi1siP2A)
- AOP+注解+令牌桶实现通用接口限流

  - 而且是不同接口对限流的速率不一样

