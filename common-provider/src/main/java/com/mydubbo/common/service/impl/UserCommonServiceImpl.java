package com.mydubbo.common.service.impl;


import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mydubbo.common.entity.UserCommon;
import com.mydubbo.common.mapper.UserCommonMapper;
import com.mydubbo.common.service.UserCommonService;
import org.springframework.cache.annotation.Cacheable;


import java.util.List;

/**
 * @author xiyou
 * 测试主从复制，读写分离和缓存的接口
 */
@Service(version = "${demo.service.version}")
public class UserCommonServiceImpl extends ServiceImpl<UserCommonMapper, UserCommon> implements UserCommonService {


    @Override
    public Boolean addUser(UserCommon user) {
        return this.baseMapper.addUser(user.getName(), user.getAge());
    }

    @DS("slave_1")
    @Override
    @Cacheable(value = "wan", key = "'one_'+#key")
    public List selectUsersFromDs(String key) {
        System.out.println(".................1.");
        return this.baseMapper.selectUsers(1);

    }

    @DS("slave_2")
    @Override
    @Cacheable(value = "yan", key = "'one_'+#key")
    public List selectUserFromDsGroup(String key) {
        System.out.println("..................2");
        return this.baseMapper.selectUsers(1);
    }
}
