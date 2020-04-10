# redis和死信队列结合

```java
    @PostMapping("/testRedisKillAndSendMq")
    @ApiOperation(value = "测试redis分布式锁结合死信队列", notes = "测试")
    public String createOptimisticOrderAop2() {
        orderService.redisKill(1, RandomUtils.nextInt(1000, 10000));
        return "正在测试订单编号" + "发送MQ消息的服务";
    }
```

核心代码

```java

```

