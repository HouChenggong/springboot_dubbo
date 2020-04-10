## rabbitMQ挂掉了怎么办

### 1. 集群部署



### 2. 做监控系统，挂掉了通知运维及时重启



### 3. 用定时任务处理rabbitMQ原本要做的工作

比如我们用rabbitMQ发邮件或者消息，可以用定时任务去做



#### 定时任务注意事项

1. 一定要用线程池

   因为定时任务本来是单线程的，所以一定要结合线程池使用
#### 示例

注意添加支持定时任务的注解@EnableScheduling

```java
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

/**
 * @author xiyou
 * 定时任务service
 */
@Service
@Slf4j
@EnableScheduling
public class DebugScheduleService {


    @Scheduled(cron = "0/10 * * * * ?")
    public void scheduleExpire() {
        log.info("定时任务处理失效订单");

    }
}
```

启动发现只有一个线程，一直在执行当前的定时任务

```
2020-04-09 21:37:20.002  INFO 22484 --- [   scheduling-1] c.m.m.d.schedule.DebugScheduleService    : 定时任务处理失效订单
2020-04-09 21:37:30.002  INFO 22484 --- [   scheduling-1] c.m.m.d.schedule.DebugScheduleService    : 定时任务处理失效订单
2020-04-09 21:37:40.002  INFO 22484 --- [   scheduling-1] c.m.m.d.schedule.DebugScheduleService    : 定时任务处理失效订单
2020-04-09 21:37:50.001  INFO 22484 --- [   scheduling-1] c.m.m.d.schedule.DebugScheduleService    : 定时任务处理失效订单
```

如果我们再添加一个定时任务，看看是不是还是一个线程

```java
   @Scheduled(cron = "0/10 * * * * ?")
    public void scheduleExpireV2() {
        log.info("V2定时任务处理失效订单");

    }
```

发现不管有几个定时任务，但是执行的只有一个线程，所以肯定不行

```
2020-04-09 21:39:00.000  INFO 21940 --- [   scheduling-1] c.m.m.d.schedule.DebugScheduleService    : 定时任务处理失效订单
2020-04-09 21:39:00.001  INFO 21940 --- [   scheduling-1] c.m.m.d.schedule.DebugScheduleService    : V2定时任务处理失效订单
2020-04-09 21:39:10.002  INFO 21940 --- [   scheduling-1] c.m.m.d.schedule.DebugScheduleService    : 定时任务处理失效订单
2020-04-09 21:39:10.002  INFO 21940 --- [   scheduling-1] c.m.m.d.schedule.DebugScheduleService    : V2定时任务处理失效订单
```



##### 多线程定时任务配置

```java
import java.util.concurrent.ThreadPoolExecutor;

/**
 * 定时任务多线程处理的通用化配置
 *
 * @Author:debug (xiyou)
 * @Date: 2019/6/29 21:45
 **/
@Configuration
public class DebugSchedulerConfig implements SchedulingConfigurer {

    @Override
    public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {

        taskRegistrar.setScheduler(taskScheduler());
    }
    @Bean
    public TaskScheduler taskScheduler() {
        ThreadPoolTaskScheduler taskScheduler = new ThreadPoolTaskScheduler();
        taskScheduler.setPoolSize(10);// 配置线程池大小，根据任务数量定制
        taskScheduler.setThreadNamePrefix("spring-task-scheduler-thread-");// 线程名称前缀
        taskScheduler.setAwaitTerminationSeconds(60);// 线程池关闭前最大等待时间，确保最后一定关闭
        taskScheduler.setWaitForTasksToCompleteOnShutdown(true);// 线程池关闭时等待所有任务完成
        taskScheduler.setRejectedExecutionHandler(new ThreadPoolExecutor.AbortPolicy());// 任务丢弃策略
        return taskScheduler;
    }
```

发现已经是多线程了

```java
2020-04-09 21:53:00.001  INFO 21876 --- [eduler-thread-2] c.m.m.d.schedule.DebugScheduleService    : V2定时任务处理失效订单
2020-04-09 21:53:00.001  INFO 21876 --- [eduler-thread-1] c.m.m.d.schedule.DebugScheduleService    : 定时任务处理失效订单
2020-04-09 21:53:10.001  INFO 21876 --- [eduler-thread-2] c.m.m.d.schedule.DebugScheduleService    : V2定时任务处理失效订单
2020-04-09 21:53:10.001  INFO 21876 --- [eduler-thread-1] c.m.m.d.schedule.DebugScheduleService    : 定时任务处理失效订单
2020-04-09 21:53:20.002  INFO 21876 --- [eduler-thread-4] c.m.m.d.schedule.DebugScheduleService    : V2定时任务处理失效订单
```

