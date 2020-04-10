## 1.准备工作

1. 依赖

```java
        <!--RabbitMQ-->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-amqp</artifactId>
        </dependency>
```

2. 连接和配置

```java
spring.rabbitmq.addresses=localhost:5672
#spring.rabbitmq.port=5672
spring.rabbitmq.username=guest
spring.rabbitmq.password=guest
spring.rabbitmq.publisher-confirms=true
spring.rabbitmq.virtual-host=/
```

## 2. 配置MQ

### 1. 新建通用配置，包括单一消费者模型，多个消费者模型

```java
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.amqp.SimpleRabbitListenerContainerFactoryConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 通用化 Rabbitmq 配置
 *
 * @author xiyou
 */
@Configuration
@Slf4j
public class DebugRabbitmqConfig {


    /**
     * 连接工厂
     */
    @Autowired
    private CachingConnectionFactory connectionFactory;

    /**
     * 消费者实例监听工厂
     */
    @Autowired
    private SimpleRabbitListenerContainerFactoryConfigurer factoryConfigurer;

    /**
     * 单一消费者
     *
     * @return
     */
    @Bean(name = "singleListenerContainer")
    public SimpleRabbitListenerContainerFactory listenerContainer() {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        //传输格式JSON
        factory.setMessageConverter(new Jackson2JsonMessageConverter());
        factory.setConcurrentConsumers(1);
        factory.setMaxConcurrentConsumers(1);
        //每次拉取一条
        factory.setPrefetchCount(1);
        factory.setTxSize(1);
        return factory;
    }

    /**
     * 多个消费者，提高吞吐量
     *
     * @return
     */
    @Bean(name = "multiListenerContainer")
    public SimpleRabbitListenerContainerFactory multiListenerContainer() {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factoryConfigurer.configure(factory, connectionFactory);
        factory.setMessageConverter(new Jackson2JsonMessageConverter());
        //确认消费模式-NONE（默认的）
        factory.setAcknowledgeMode(AcknowledgeMode.NONE);
        factory.setConcurrentConsumers(5);
        factory.setMaxConcurrentConsumers(10);
        //每次拉取10条消息
        factory.setPrefetchCount(10);
        return factory;
    }

    /**
     * 发送消息的核心
     *
     * @return
     */
    @Bean
    public RabbitTemplate rabbitTemplate() {
        //发送消息确认模式
        connectionFactory.setPublisherConfirms(true);
        //对于发布确认，template要求CachingConnectionFactory的publisherConfirms属性设置为true。
        //如果要消息确认，则必须实现Callback，也就是下面的
        connectionFactory.setPublisherReturns(true);
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMandatory(true);
        rabbitTemplate.setConfirmCallback(new RabbitTemplate.ConfirmCallback() {
            @Override
            public void confirm(CorrelationData correlationData, boolean ack, String cause) {
                log.info("消息发送成功:correlationData({}),ack({}),cause({})", correlationData, ack, cause);
            }
        });
        rabbitTemplate.setReturnCallback(new RabbitTemplate.ReturnCallback() {
            @Override
            public void returnedMessage(Message message, int replyCode, String replyText, String exchange, String routingKey) {
                log.warn("消息丢失:exchange({}),route({}),replyCode({}),replyText({}),message:{}", exchange, routingKey, replyCode, replyText, message);
            }
        });
        return rabbitTemplate;
    }
    }
```

### 2.添加自己的队列

在上述的类中DebugRabbitmqConfig

```
    // ----------------------------------------------------秒杀成功的MQ-


    //构建异步发送邮箱通知的消息模型
    @Bean
    public Queue successEmailQueue() {
        //名字和持久化
        return new Queue(DebugRabbitConsts.MAIL_SUCCESS_QUEUE, true);
    }

    /**
     * 持久化并且不自动删除
     *
     * @return
     */
    @Bean
    public TopicExchange successEmailExchange() {
        //名字、持久化、自动删除
        return new TopicExchange(DebugRabbitConsts.MAIL_SUCCESS_EXCHANGE, true, false);
    }

    /**
     * 将一个交换机和路由绑定到一个队列中
     *
     * @return
     */
    @Bean
    public Binding successEmailBinding() {
        return BindingBuilder.bind(successEmailQueue()).to(successEmailExchange()).with(DebugRabbitConsts.MAIL_SUCCESS_ROUTING);
    }
    // ----------------------------------------------------秒杀成功的MQ-

```

3. 用到的名词

```java
/**
 * @author xiyou
 */
public class DebugRabbitConsts {
    /**
     * 秒杀成功的队列名称
     */
    public static final String MAIL_SUCCESS_QUEUE = "mailSuccessQueue";
    /**
     * 秒杀成功的路由
     */
    public static final String MAIL_SUCCESS_ROUTING = "mailSuccessRoutingKey";


    /**
     * 秒杀成功的交换机
     */
    public static final String MAIL_SUCCESS_EXCHANGE = "mailSuccessExchange";
}
```

### 3. 新建发送服务

```java
import cn.monitor4all.miaoshadao.dao.StockOrder;
import cn.monitor4all.miaoshadao.mapper.StockOrderMapper;
import cn.monitor4all.miaoshaservice.debug.DebugRabbitConsts;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageDeliveryMode;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.AbstractJavaTypeMapper;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author xiyou
 * debug rabbitMQ发送消息服务
 */
@Slf4j
@Service
public class DebugRabbitSenderService {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    /**
     * 秒杀成功的订单mapper主要用来查询订单信息
     */

    @Autowired
    private StockOrderMapper orderMapper;


    /**
     * 秒杀成功异步发送邮件通知消息
     *
     * @param orderNo 秒杀成功之后的订单编号
     */
    public void sendKillSuccessEmailMsg(int orderNo) {
        log.info("秒杀成功异步发送邮件通知消息-准备发送消息：{}", orderNo);

        try {
            StockOrder info = orderMapper.selectByPrimaryKey(orderNo);
            if (info != null) {
                //TODO:rabbitmq发送消息的逻辑
                rabbitTemplate.setMessageConverter(new Jackson2JsonMessageConverter());
                //绑定交换机
                rabbitTemplate.setExchange(DebugRabbitConsts.MAIL_SUCCESS_EXCHANGE);
                //绑定路由和路由之后，他会把它绑定到指定的队列
                rabbitTemplate.setRoutingKey(DebugRabbitConsts.MAIL_SUCCESS_ROUTING);


                //TODO：将info充当消息发送至队列
                rabbitTemplate.convertAndSend(info, new MessagePostProcessor() {
                    @Override
                    public Message postProcessMessage(Message message) throws AmqpException {
                        MessageProperties messageProperties = message.getMessageProperties();
                        //保证消息可靠性，进行持久化
                        messageProperties.setDeliveryMode(MessageDeliveryMode.PERSISTENT);
                        //设置消息头，并指定确切的类型，这样消费者就可以直接用对象接收
                        messageProperties.setHeader(AbstractJavaTypeMapper.DEFAULT_CONTENT_CLASSID_FIELD_NAME, StockOrder.class);
                        return message;
                    }
                });
            }

        } catch (Exception e) {
            log.error("秒杀成功异步发送邮件通知消息-发生异常，消息为：{}", orderNo, e.fillInStackTrace());
        }
    }
}
```

### 4. 新建消费者服务

```java
import cn.monitor4all.miaoshadao.dao.StockOrder;
import cn.monitor4all.miaoshaservice.debug.DebugRabbitConsts;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

/**
 * @author xiyou
 * debug rabbitMQ消费服务
 */
@Service
@Slf4j
public class DebugRabbitConsumer {

    /**
     * 秒杀异步邮件通知-接收消息
     * queues接受的队列
     * containerFactory接受的工厂，现在用的是DebugRabbitmqConfig里面的单一消费者模式的工厂
     */
    @RabbitListener(queues = {DebugRabbitConsts.MAIL_SUCCESS_QUEUE}, containerFactory = "singleListenerContainer")
    public void consumeEmailMsg(StockOrder info) {
        try {
            log.info("秒杀异步邮件通知-接收消息:{}", info);

            //TODO:真正的发送邮件....
            log.info("这里应该是真正发送邮件的逻辑..........如果发送成功，可以更新下数据库的字段，表示发送成功，否则就执行相关邮件发送失败逻辑");

        } catch (Exception e) {
            log.error("秒杀异步邮件通知-接收消息-发生异常：", e.fillInStackTrace());
        }
    }
}
```

### 5. 测试流程

```java
/**
 * @author xiyou
 */
@RestController
@Api("debug秒杀相关")
@RequestMapping("/debug")
public class DebugRabbitController {


    @Autowired
    private DebugRabbitSenderService senderService;


    @GetMapping("/testSendMq/{sid}")
    @ApiOperation(value = "测试发送消息能不能被消费者接受到", notes = "测试")
    public String createOptimisticOrderAop2(@PathVariable int sid) {
        senderService.sendKillSuccessEmailMsg(sid);
        return "正在测试订单编号" + sid + "发送MQ消息的服务";
    }
}
```

结果：

```java
DebugRabbitSenderService    : 秒杀成功异步发送邮件通知消息-准备发送消息：1
DebugRabbitmqConfig          : 消息发送成功:correlationData(null),ack(true),cause(null)
DebugRabbitConsumer     : 秒杀异步邮件通知-接收消息:StockOrder(id=1, sid=1, name=iphone, DebugRabbitConsumer     : 这里应该是真正发送邮件的逻辑..........如果发送成功，可以更新下数据库的字段，表示发送成功，否则就执行相关邮件发送失败逻辑

```

