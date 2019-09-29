package com.mydubbo.es.dao;

import com.mydubbo.es.entity.UserEntity;
import org.springframework.data.repository.CrudRepository;

public interface UserDao  extends CrudRepository<UserEntity,String> {
}
