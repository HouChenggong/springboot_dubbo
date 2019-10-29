package com.mydubbo.common.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mydubbo.common.entity.UserCommon;
import com.mydubbo.common.mapper.UserCommonMapper;
import com.mydubbo.common.service.IUserServiceOne;
import com.mydubbo.common.service.IUserServiceTwo;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service(version = "${demo.service.version}")
public class UserServiceTwoImpl extends ServiceImpl<UserCommonMapper, UserCommon> implements IUserServiceTwo {


    /**
     * 是一个抛异常的事物
     *
     * @param user
     * @return
     */
    @Override
    public Boolean addRequiredExceptionTwo(UserCommon user) {
        this.baseMapper.addUser(user.getName(), user.getAge());
        throw new RuntimeException();
    }


    /**
     * 普通的insert事务
     *
     * @param user
     * @return
     */
    @Override
    public Boolean addRequiredTwo(UserCommon user) {
        return this.baseMapper.addUser(user.getName(), user.getAge());
    }
}
