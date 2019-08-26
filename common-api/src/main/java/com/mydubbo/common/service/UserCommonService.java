package com.mydubbo.common.service;


import com.mydubbo.common.entity.UserCommon;

import java.util.List;

public interface UserCommonService {

  Boolean addUser(UserCommon user);

  List selectUsersFromDs();

  List selectUserFromDsGroup();
}
