package com.demo.mapper;
import com.demo.model.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {

    public User queryById(Long id);

    public void updateUserById(Long id,String name);

    public void addUser(String name);
}
