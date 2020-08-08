package com.junjie.springbootdata.controller;

import com.junjie.springbootdata.mapper.UserMapper;
import com.junjie.springbootdata.pojo.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserController {

    @Autowired
    private UserMapper userMapper;

    @GetMapping("/queryUserList")
    public List<User> queryUserList(){
        List<User> userList = userMapper.queryUserList();
        for(User user: userList){
            System.out.println(user);
        }
        return userList;
    }

    @GetMapping("/queryUserById")
    public User queryUserById(){
        User user = userMapper.queryUserById(0);
        System.out.println(user);
        return user;
    }

    @GetMapping("/addUser")
    public String addUser (){
        userMapper.addUser(new User(9, "heihei", "efefef", "asdf@qq.com"));
        return "ok";
    }

    @GetMapping("/updateUser")
    public String updateUser(){
        userMapper.updateUser(new User(5, "after", "after", "after@qq.com"));
        return "ok";
    }

    @GetMapping("/deleteUser")
    public String deleteUser(){
        userMapper.deleteUser(9);
        return "ok";
    }

    @GetMapping("/addUserWithoutId")
    public String addUserWithoutId (){
        userMapper.addUserWithoutId(new User("without", "without", "without@qq.com"));
        return "ok";
    }
}
