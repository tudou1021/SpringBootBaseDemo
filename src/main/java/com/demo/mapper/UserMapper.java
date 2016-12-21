package com.demo.mapper;
import com.demo.model.User;
import org.apache.ibatis.annotations.Mapper;

public interface UserMapper {

     User queryById(Long id);

     void updateUserById(Long id,String name);

     void addUser(String name);

     void deleteUserById(Long id);
}
