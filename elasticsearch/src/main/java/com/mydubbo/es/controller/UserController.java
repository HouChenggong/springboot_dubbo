package com.mydubbo.es.controller;


import com.mydubbo.es.dao.UserDao;
import com.mydubbo.es.entity.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.Optional;

@RestController
public class UserController {
    @Autowired
    private UserDao userDao;

    @GetMapping("/addUser")
    public UserEntity addUser() {
        UserEntity user = new UserEntity();
        String userName = new Date().toString();
        user.setName(userName);
        user.setId(String.valueOf(userName.hashCode()));
        user.setAge(18);
        user.setSex("man");
        return userDao.save(user);
    }


    @GetMapping("/updateUser")
    public UserEntity updateUser(String userId) {
        UserEntity user = new UserEntity();
        String userName = new Date().toString();
        user.setName(userName);
        user.setId(userId);
        user.setAge(15);
        user.setSex("man");
        return userDao.save(user);
    }


    @GetMapping("/getUser")
    public Optional<UserEntity> getUser(String userId) {
        return userDao.findById(userId);
    }

    @GetMapping("/delUser")
    public Optional<UserEntity> delUser(String userId) {
        userDao.deleteById(userId);
        return userDao.findById(userId);
    }
}
