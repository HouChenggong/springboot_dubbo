package com.mydubbo.common.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.mydubbo.common.entity.UserCommon;
import com.mydubbo.common.service.ITestTransactionService;
import com.mydubbo.common.service.IUserServiceOne;
import com.mydubbo.common.service.IUserServiceTwo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service(version = "${demo.service.version}")
public class TestTransactionServiceImpl implements ITestTransactionService {


    @Autowired
    private IUserServiceOne serviceOne;


    @Autowired
    private IUserServiceTwo serviceTwo;


    /**
     * 目的：REQUIRED的传播行为下，大方法里面发生异常，大方法和小方法怎么回滚？
     * 都是包含性事务的情况下，在事务的最外层抛出异常
     * 结果：大方法和两个小方法都回滚了
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void test1() {
        UserCommon user1 = new UserCommon();
        user1.setName("包含性事务1A");
        Boolean isTrue = serviceOne.addRequiredOne(user1);
        System.out.println("1:" + isTrue);
        UserCommon user2 = new UserCommon();
        user2.setName("包含性事务1B");
        Boolean isTrue2 = serviceTwo.addRequiredTwo(user2);
        System.out.println("2:" + isTrue2);
        /**
         * 结果：
         * 1. 如果加入下面的一行代码，发现代码要回滚，在回滚之前插入的记录对其它线程不共享
         * 而且如果是自增id，这两个插入的数据比如是37，38发送了回滚，下次会从39开始
         * 2. 如果注释掉就正常执行
         */
//        throw new RuntimeException();
    }


    /**
     * 目的：REQUIRED的传播行为下，小方法里面发生异常，大方法和小方法怎么回滚？
     * <p>
     * 结果：大方法和两个小方法都回滚了
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void test2() {
        UserCommon user1 = new UserCommon();
        user1.setName("包含性事务2A");
        Boolean isTrue = serviceOne.addRequiredOne(user1);
        System.out.println("1:" + isTrue);
        UserCommon user2 = new UserCommon();
        user2.setName("包含性事务2B");
        /**
         * 因为下面的事务要抛出异常，所以整个代码块要发生回滚，在回滚之前插入的记录对其它线程不共享
         * 而且如果是自增id，这两个插入的数据比如是37，38发送了回滚，下次会从39开始
         */
        Boolean isTrue2 = serviceTwo.addRequiredExceptionTwo(user2);
        System.out.println("2:" + isTrue2);
    }


    /**
     * 目的：REQUIRED的传播行为下，小方法里面发生异常但是给捕获了，大方法和小方法怎么回滚？
     * <p>
     * 结果：大方法和两个小方法都正常执行，不会发生回滚
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void test3() {
        UserCommon user1 = new UserCommon();
        user1.setName("包含性事务3A");
        Boolean isTrue = serviceOne.addRequiredOne(user1);
        System.out.println("1:" + isTrue);
        UserCommon user2 = new UserCommon();
        user2.setName("包含性事务3B");
        try {
            Boolean isTrue2 = serviceTwo.addRequiredExceptionTwo(user2);
            System.out.println("2:" + isTrue2);
        } catch (Exception e) {
            /**
             * 因为异常被捕获，所以不会发生回滚，但是自己要手动在代码里写sql回滚
             */
            System.out.println("包含型事务3B回滚，手动执行相关回滚逻辑，即删除刚才的两条记录");
        }

    }


    /**
     * 目的：测试不加事务，但是小方法插入数据后，发生异常
     * <p>
     * <p>
     * 结果是：都插入了数据，代码没有回滚
     */
    @Override
    public void test4() {
        UserCommon user1 = new UserCommon();
        user1.setName("包含性事务4A");
        Boolean isTrue = serviceOne.addRequiredOne(user1);
        System.out.println("1:" + isTrue);
        UserCommon user2 = new UserCommon();
        user2.setName("包含性事务4B");
        Boolean isTrue2 = serviceTwo.addRequiredExceptionTwo(user2);
        System.out.println("2:" + isTrue2);
    }


    /**
     * 目的：测试默认的传播行为
     * addRequiredOne正常执行
     * addRequiredExceptionTwo里面会抛出一个异常
     * 结果是：都插入了数据，代码没有回滚
     */

    @Override
    @Transactional
    public void test5() {
        UserCommon user1 = new UserCommon();
        user1.setName("包含性事务4A");
        Boolean isTrue = serviceOne.addRequiredOne(user1);
        System.out.println("1:" + isTrue);
        UserCommon user2 = new UserCommon();
        user2.setName("包含性事务4B");
        Boolean isTrue2 = serviceTwo.addRequiredExceptionTwo(user2);
        System.out.println("2:" + isTrue2);
    }

    /***
     * 到这里5个方法执行完成，说明了一个事情，在执行增删改的时候，无论大方法还是小方法一定要添加事务的注解
     *
     */


}
