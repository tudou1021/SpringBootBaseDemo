package com.demo.service;

import com.demo.mapper.UserMapper;
import com.demo.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public User queryById(Long id) {
        return userMapper.queryById(id);
    }

    @Override
    public User updateUserById(Long id, String name) {
        userMapper.updateUserById(id, name);
        return userMapper.queryById(id);
    }

    @Override
    public void addUser(String name) {
        userMapper.addUser(name);
    }
}
