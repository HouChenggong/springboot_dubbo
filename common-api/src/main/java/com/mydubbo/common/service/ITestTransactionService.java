package com.mydubbo.common.service;

/**
 * 测试事务的传播行为
 * 123均为包含性事务的测试
 * 4是测试不加事务的注解的
 * 5是测试默认传播行为的，即不加传播行为注解
 */
public interface ITestTransactionService {
    void test1();

    void test2();

    void test3();

    void test4();

    void test5();
}
