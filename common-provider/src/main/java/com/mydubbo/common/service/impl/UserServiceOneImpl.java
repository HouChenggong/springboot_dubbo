package com.mydubbo.common.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mydubbo.common.entity.UserCommon;
import com.mydubbo.common.mapper.UserCommonMapper;
import com.mydubbo.common.service.IUserServiceOne;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service(version = "${demo.service.version}")
public class UserServiceOneImpl extends ServiceImpl<UserCommonMapper, UserCommon> implements IUserServiceOne {
    @Override
    public Boolean addRequiredOne(UserCommon user) {
        return this.baseMapper.addUser(user.getName(), user.getAge());
    }


}
