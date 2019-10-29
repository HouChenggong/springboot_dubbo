package com.mydubbo.common.service;

import com.mydubbo.common.entity.UserCommon;

public interface IUserServiceOne {

    /**
     * 普通的包含型事务:
     * 外部事物包含了内部事物组成一个统一的事物（REQUIRED）
     *
     * @param user
     * @return
     */
    Boolean addRequiredOne(UserCommon user);


//    /**
//     * 嵌套型事务:
//     * 该事物如果内部事物回滚，不会触发外部事物的回滚，
//     * 但外部事物的回滚会导致内部事物回滚。下面看示例：
//     *
//     * @param user
//     * @return
//     */
//    Boolean addNestedOne(UserCommon user);


}
