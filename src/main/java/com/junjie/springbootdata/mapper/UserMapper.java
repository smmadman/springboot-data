package com.junjie.springbootdata.mapper;

import com.junjie.springbootdata.pojo.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

//这个注解表示了这是一个mybatis的mapper类
@Mapper
@Repository
public interface UserMapper {

    List<User> queryUserList();

    User queryUserById(int id);

    int addUser(User user);

    int updateUser(User user);

    int deleteUser(int id);

    int addUserWithoutId(User user);

    int countLines();

    User queryUserByName(String name);

    List<Integer> queryIndexByPage(int start, int size);
}
