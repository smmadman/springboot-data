package com.junjie.springbootdata.service;

import com.junjie.springbootdata.mapper.UserMapper;
import com.junjie.springbootdata.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {

    @Autowired
    UserMapper userMapper;

    public List<User> queryUserList() {
        return userMapper.queryUserList();
    }

    public User queryUserById(int id) {
        return userMapper.queryUserById(id);
    }

    public int addUser(User user) {
        return userMapper.addUser(user);
    }

    public int updateUser(User user) {
        return userMapper.updateUser(user);
    }

    public int deleteUser(int id) {
        return userMapper.deleteUser(id);
    }

    public int addUserWithoutId(User user) {
        return userMapper.addUserWithoutId(user);
    }

    public int countLines() {
        return userMapper.countLines();
    }

    public User queryUserByName(String name) {
        return userMapper.queryUserByName(name);
    }

    public List<List<Integer>> pagesIndex(int perPageCounts){

        List<List<Integer>> allIndex = new ArrayList<>();

        int allCounts = userMapper.countLines();

        int pages;
        if(allCounts % perPageCounts == 0){
            pages = allCounts / perPageCounts;
        }else{
            pages = allCounts / perPageCounts + 1;
        }

        for(int i = 1; i <= pages; i++){
            int start = (i - 1) * perPageCounts;
            List<Integer> list = userMapper.queryIndexByPage(start, perPageCounts);
            allIndex.add(list);
        }

        return allIndex;
    }
}
