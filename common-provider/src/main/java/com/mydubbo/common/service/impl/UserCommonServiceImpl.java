package com.mydubbo.common.service.impl;


import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mydubbo.common.entity.UserCommon;
import com.mydubbo.common.mapper.UserCommonMapper;
import com.mydubbo.common.service.UserCommonService;


import java.util.List;

@Service(version = "${demo.service.version}")
public class UserCommonServiceImpl extends ServiceImpl<UserCommonMapper, UserCommon> implements UserCommonService  {




  @Override
  public Boolean addUser(UserCommon user) {
   return this.baseMapper.addUser(user.getName(), user.getAge());
  }

  @DS("slave_1")
  @Override
  public List selectUsersFromDs() {
    return this.baseMapper.selectUsers(1);
  }

  @DS("slave_2")
  @Override
  public List selectUserFromDsGroup() {
    return this.baseMapper.selectUsers(1);
  }
}
