package cn.net.health.user.thread;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

/**
 * @author xiyou
 * @version 1.2
 * @date 2019/12/16 15:21
 */
public class TestHystrix {
    private final static Logger LOGGER = LoggerFactory.getLogger(TestHystrix.class);

    public static void main(String[] args) throws Exception {
        CommandOrder commandPhone = new CommandOrder("手机");
        CommandOrder command = new CommandOrder("电视");


        //阻塞方式执行
        String execute = commandPhone.execute();
        LOGGER.info("execute=[{}]", execute);

        //异步非阻塞方式
        Future<String> queue = command.queue();
        String value = queue.get(200, TimeUnit.MILLISECONDS);
        LOGGER.info("value=[{}]", value);


        CommandUser commandUser = new CommandUser("张三");
        String name = commandUser.execute();
        LOGGER.info("name=[{}]", name);
    }
}
