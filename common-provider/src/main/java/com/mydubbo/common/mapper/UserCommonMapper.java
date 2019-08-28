package com.mydubbo.common.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mydubbo.common.entity.UserCommon;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;


/**
 * @author xiyou
 * 测试读写分离和主从复制的接口
 */
@Mapper
public interface UserCommonMapper extends BaseMapper<UserCommon> {

  @Insert("INSERT INTO user (name,age) values (#{name},#{age})")
  boolean addUser(@Param("name") String name, @Param("age") Integer age);

  @Select("SELECT * FROM user where age > #{age}")
  List<UserCommon> selectUsers(@Param("age") Integer age);
}
