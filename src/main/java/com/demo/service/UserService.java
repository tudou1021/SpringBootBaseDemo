package com.demo.service;

import com.demo.model.User;

public interface UserService {

    public User queryById(Long id);

    public User updateUserById(Long id,String name);

    public void addUser(String name);

    public void deleteUserById(Long id) throws Exception;
}
