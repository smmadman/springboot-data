package com.junjie.springbootdata.controller;

import com.junjie.springbootdata.mapper.UserMapper;
import com.junjie.springbootdata.pojo.User;
import com.junjie.springbootdata.service.UserService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/queryUserList")
    public List<User> queryUserList(){
        List<User> userList = userService.queryUserList();
        for(User user: userList){
            System.out.println(user);
        }
        return userList;
    }

    @GetMapping("/queryUserById")
    public User queryUserById(){
        User user = userService.queryUserById(0);
        System.out.println(user);
        return user;
    }

    @GetMapping("/addUser")
    public String addUser (){
        userService.addUser(new User(9, "heihei", "efefef", "asdf@qq.com"));
        return "ok";
    }

    @GetMapping("/updateUser")
    public String updateUser(){
        userService.updateUser(new User(5, "after", "after", "after@qq.com"));
        return "ok";
    }

    @GetMapping("/deleteUser")
    public String deleteUser(){
        userService.deleteUser(9);
        return "ok";
    }

    @GetMapping("/addUserWithoutId")
    public String addUserWithoutId (){
        userService.addUserWithoutId(new User("without", "without", "without@qq.com"));
        return "ok";
    }

    @GetMapping("/countLines")
    public String countLines(){
        System.out.println(userService.countLines());
        return "ok";
    }

    @GetMapping("/queryUserByName")
    public String queryUserByName(){
        System.out.println(userService.queryUserByName("33"));
        return "ok";
    }

    @GetMapping("/queryIndexByPage")
    public String queryIndexByPage(){
        List<List<Integer>> list = userService.pagesIndex(10000);
        System.out.println(list.size());
        System.out.println(list.get(0).size());
        return "ok";
    }
}
