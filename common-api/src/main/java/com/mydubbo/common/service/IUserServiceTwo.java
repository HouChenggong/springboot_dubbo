package com.mydubbo.common.service;

import com.mydubbo.common.entity.UserCommon;

public interface IUserServiceTwo {

//    /**
//     * 普通的嵌套型事务NESTED：
//     * 该事物如果内部事物回滚，不会触发外部事物的回滚，但外部事物的回滚会导致内部事物回滚。
//     *
//     * @param user
//     * @return
//     */
//    Boolean addNested(UserCommon user);

    /**
     * addRequiredException是一个抛异常的Required事物。
     *
     * @param user
     * @return
     */
    Boolean addRequiredExceptionTwo(UserCommon user);


//    /**
//     * 是挂起事物
//     * @param user
//     * @return
//     */
//    Boolean addRequiresNew(UserCommon user);



    /**
     * 普通的包含型事务:
     * 外部事物包含了内部事物组成一个统一的事物（REQUIRED）
     *
     * @param user
     * @return
     */
    Boolean addRequiredTwo(UserCommon user);

}
