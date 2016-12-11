package com.demo.controller;

import com.demo.model.User;
import com.demo.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Api
@RestController
@RequestMapping(value = "/user")
public class UserController {

    private Logger logger = Logger.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    @ApiOperation(value = "获取用户信息", notes = "根据用户ID获取用户详情")
    @ApiImplicitParam(name = "id", value = "用户ID", paramType = "path",required = true, dataType = "Long")
    @ResponseBody
    @RequestMapping(value = "/{id}",method = RequestMethod.GET)
    public User queryUser(@PathVariable Long id) {
        User user = userService.queryById(id);
        return user;
    }

    @ApiOperation(value = "更新用户信息", notes = "根据用户ID更新用户信息，并返回最新更新结果")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "用户ID",paramType = "path",required = true, dataType = "Long"),
            @ApiImplicitParam(name = "name", value = "用户名称",paramType = "path",required = true, dataType = "String")
    })
    @ResponseBody
    @RequestMapping(value = "/{id}/{name}",method = RequestMethod.GET)
    public User updateUser(@PathVariable Long id, @PathVariable String name) {
        User user = userService.updateUserById(id, name);
        return user;
    }

    @ApiOperation(value = "添加用户", notes = "输入用户名添加用户信息")
    @ApiImplicitParam(name = "name", value = "用户姓名", paramType = "body",required = true, dataType = "String")
    @ResponseBody
    @RequestMapping(value = "",method = RequestMethod.POST)
    public Map addUser(@RequestBody String name) {
        Map<String, String> retMap = new HashMap<>();
        try {
            userService.addUser(name);
            retMap.put("result", "true");
            retMap.put("msg", "添加成功");
        } catch (Exception e) {
            e.printStackTrace();
            retMap.put("result", "false");
            retMap.put("msg", "添加失败");
        }
        return retMap;
    }

    @ApiOperation(value = "删除用户",notes = "根据用户ID删除用户")
    @ApiImplicitParam(name = "id",value = "用户ID",paramType = "path",required = true,dataType = "Long")
    @RequestMapping(value = "/{id}",method = RequestMethod.DELETE)
    public Map deleteUser(@PathVariable Long id){
        Map<String, String> retMap = new HashMap<>();
        try {
            userService.deleteUserById(id);
            retMap.put("result", "true");
            retMap.put("msg", "操作成功");
        } catch (Exception e) {
            e.printStackTrace();
            retMap.put("result", "false");
            retMap.put("msg", "操作失败");
        }
        return retMap;
    }
}