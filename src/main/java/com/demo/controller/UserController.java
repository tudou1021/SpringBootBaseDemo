package com.demo.controller;

import com.demo.model.User;
import com.demo.service.UserService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class UserController {

    private Logger logger=Logger.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    @RequestMapping("/hello")
    public String index() {
        return "Hello World";
    }

    @ResponseBody
    @RequestMapping("/user/{id}")
    public User queryUser(@PathVariable Long id){
        User user=userService.queryById(id);
        return user;
    }

    @ResponseBody
    @RequestMapping("/user/{id}/{name}")
    public User updateUser(@PathVariable Long id,@PathVariable String name){
        User user=userService.updateUserById(id,name);
        return user;
    }

    @ResponseBody
    @RequestMapping("/user/add/{name}")
    public Map addUser(@PathVariable String name){
        Map<String,String> retMap=new HashMap<>();
        try {
            userService.addUser(name);
            retMap.put("result","true");
            retMap.put("msg","添加成功");
        } catch (Exception e) {
            e.printStackTrace();
            retMap.put("result","false");
            retMap.put("msg","添加失败");
        }
        return retMap;
    }
}