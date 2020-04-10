# 异步发送邮件、短信

注意：这里承接的是上一篇文章：用MQ发送消息

## 写一个异步的发送接口

```java
import cn.monitor4all.miaoshadao.dao.MailDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;


/**
 * 异步通用的发送邮件服务
 *
 * @Author:debug (SteadyJack)
 * @Date: 2019/6/22 10:09
 **/
@Service
@EnableAsync
@Slf4j
public class DebugMailService {


    /**
     * 发送简单文本文件
     */
    @Async
    public void sendSimpleEmail(final MailDto dto) {
        try {
            System.out.println("假装发送了邮件、短信" + dto.toString());
            //这里执行具体的邮件或者短信发送方法，这里不再写邮件发送的接口
            log.info("发送成功!" + dto.toString());
        } catch (Exception e) {
            log.error("发生异常： ", e.fillInStackTrace());
        }
    }


}
```

## 2. 在MQ的消费者端调用具体逻辑

```java
@Service
@Slf4j
public class DebugRabbitConsumer {

    @Autowired
    private DebugMailService mailService;

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
            mailService.sendSimpleEmail(new MailDto("邮件", "这是邮件内容", new String[]{"xxx@qq.com", "xbbb@qq.com"}));
        } catch (Exception e) {
            log.error("秒杀异步邮件通知-接收消息-发生异常：", e.fillInStackTrace());
        }
    }
}
```

 

