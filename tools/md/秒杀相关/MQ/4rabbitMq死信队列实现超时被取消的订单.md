## 0. 为啥引入死信队列

```java
订单状态，0秒杀成功，1已经付款，-1 超时未支付被取消  -2 用户自己取消订单
```

-1的状态一般我们都是用定时任务去处理，但是太麻烦，而引入死信队列就不用定时任务了

## 1. 死信队列

延时处理消息

![](E:\2020\code\springboot_dubbo\tools\md\秒杀相关\MQ\img\dead.png)

所以死信队列有一个自己的队列和交换机，还有真正的队列和交换机

### 1.1 创建死信队列

```java
 //构建秒杀成功之后-订单超时未支付的死信队列消息模型

    @Bean
    public Queue successKillDeadQueue() {
        Map<String, Object> argsMap = Maps.newHashMap();
        argsMap.put("x-dead-letter-exchange", DebugRabbitConsts.MAIL_SUCCESS_DEAD_QUEUE);
        argsMap.put("x-dead-letter-routing-key", DebugRabbitConsts.MAIL_SUCCESS_DEAD_ROUTING);
        return new Queue(DebugRabbitConsts.MAIL_SUCCESS_DEAD_QUEUE, true, false, false, argsMap);
    }

    //基本交换机
    @Bean
    public TopicExchange successKillDeadProdExchange() {
        return new TopicExchange(DebugRabbitConsts.MAIL_SUCCESS_DEAD_EXCHANGE, true, false);
    }

    //创建基本交换机+基本路由 -> 死信队列 的绑定
    @Bean
    public Binding successKillDeadProdBinding() {
        return BindingBuilder.bind(successKillDeadQueue()).to(successKillDeadProdExchange()).with(DebugRabbitConsts.MAIL_SUCCESS_DEAD_ROUTING);
    }

    //真正的队列
    @Bean
    public Queue successKillRealQueue() {
        return new Queue(DebugRabbitConsts.MAIL_SUCCESS_DEAD_REAL_QUEUE,true);
    }

    //死信交换机
    @Bean
    public TopicExchange successKillDeadExchange() {
        return new TopicExchange(DebugRabbitConsts.MAIL_SUCCESS_DEAD_REAL_EXCHANGE, true, false);
    }

    //死信交换机+死信路由->真正队列 的绑定
    @Bean
    public Binding successKillDeadBinding() {
        return BindingBuilder.bind(successKillRealQueue()).to(successKillDeadExchange()).with(DebugRabbitConsts.MAIL_SUCCESS_DEAD_REAL_ROUTING);
    }
```

```java
    /**
     * 秒杀成功的死信队列
     */
    public static final String MAIL_SUCCESS_DEAD_QUEUE = "mailSuccessDeadQueue";


    /**
     * 秒杀成功的死信交换机
     */
    public static final String MAIL_SUCCESS_DEAD_EXCHANGE = "mailSuccessDeadExchange";

    
    /**
     * 秒杀成功的死信路由
     */
    public static final String MAIL_SUCCESS_DEAD_ROUTING= "mailSuccessDeadRouting";
    

    /**
     * 秒杀成功的死信队列-真正的队列
     */
    public static final String MAIL_SUCCESS_DEAD_REAL_QUEUE = "mailSuccessDeadRealQueue";


    /**
     * 秒杀成功的死信交换机-真正的队列
     */
    public static final String MAIL_SUCCESS_DEAD_REAL_EXCHANGE = "mailSuccessDeadRealExchange";




    /**
     * 秒杀成功的死信路由-真正的队列
     */
    public static final String MAIL_SUCCESS_DEAD_REAL_ROUTING= "mailSuccessDeadRealRouting";
```

### 1.2 如何使用死信队列

1. 比如我们用乐观锁机制，在用户秒杀成功后进入死信队列

```java

/**
     * 测试用户秒杀结束后，进入死信队列
     *
     * @param sid
     * @return
     */
    @GetMapping("/testDead/{sid}")
    @ApiOperation(value = "测试用户秒杀结束后，进入死信队列", notes = "死信队列")
    @ResponseBody
    public String createOptimisticOrder(@PathVariable int sid) {

        int id;
        try {
            id = orderService.createOptimisticOrderAndSendMsg(sid);
            log.info("购买成功，剩余库存为: [{}]", id);
        } catch (Exception e) {
            log.error("购买失败：[{}]", e.getMessage());
            return "购买失败，库存不足";
        }
        return String.format("购买成功，剩余库存为：%d", id);
    }
```

2. 核心代码先发短信或者邮件消息，然后进入死信队列

```
    @Override
    public int createOptimisticOrderAndSendMsg(int sid) {
        //校验库存
        Stock stock = checkStock(sid);
        //乐观锁更新库存
        saleStockOptimistic(stock);
        //创建订单
        int id = createOrder(stock);
        //发送消息
        senderService.sendKillSuccessEmailMsg(id);
        //入死信队列，如果超时，会自动发送消息
        senderService.sendKillSuccessOrderExpireMsg(id);
        int success = stock.getCount() - (stock.getSale() + 1);
        return success;
    }
```

