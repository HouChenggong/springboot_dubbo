package com.mydubbo.common.service;


import com.mydubbo.common.entity.UserCommon;

import java.util.List;

/**
 * 当前接口是测试主从复制，读写分离的接口
 * @author xiyou
 */
public interface UserCommonService {

    Boolean addUser(UserCommon user);

    List selectUsersFromDs(String key);

    List selectUserFromDsGroup(String key);
}
