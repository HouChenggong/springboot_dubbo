package com.mydubbo.common.service;


import com.mydubbo.common.entity.WordDTO;

/**
 * 原始API接口，勿删
 *
 * @author xiyou
 */
public interface ApiDemoService {


    public String sayHello(String name);

    /**
     * 测试dubbo序列号出现的问题
     * 问题地址：
     * https://mp.weixin.qq.com/s/DLwNcx-DKS-uJpZVSg_3VA
     * 发现第一个序列号的没有问题
     *
     * @param wordDTO
     * @return
     */
    public String testError(WordDTO wordDTO);

}
