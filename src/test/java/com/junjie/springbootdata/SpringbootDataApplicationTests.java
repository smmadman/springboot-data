package com.junjie.springbootdata;

import com.junjie.springbootdata.mapper.UserMapper;
import com.junjie.springbootdata.pojo.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisTemplate;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

@SpringBootTest
class SpringbootDataApplicationTests {

    @Autowired
    DataSource dataSource;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private RedisTemplate redisTemplate;

    @Test
    void redisTest(){

        redisTemplate.opsForValue().set("mykey", "嘿嘿");
        System.out.println(redisTemplate.opsForValue().get("mykey"));

        RedisConnection connection = redisTemplate.getConnectionFactory().getConnection();

    }

    @Test
    void contextLoads() throws SQLException {//com.zaxxer.hikari.HikariDataSource
        //查看默认数据源
        System.out.println(dataSource.getClass() + "+++++++++++++++++++++++++++++");

        Connection connection = dataSource.getConnection();
        System.out.println(connection + "+++++++++++++++++++++++++++++");
        connection.close();
    }

    @Test
    void writeData10000(){//12s
        long startTime = System.currentTimeMillis();    //获取开始时间

        for(int i=0; i < 10000; i++){
            userMapper.addUserWithoutId(new User(Integer.toString(i+100000), ("password" + Integer.toString(i+10)), (Integer.toString(i + 10) + "@qq.com")));
            System.out.println("i == " + i);
        }

        long endTime = System.currentTimeMillis();    //获取结束时间

        System.out.println("程序运行时间：" + (endTime - startTime) / 1000 + "s");    //输出程序运行时间
    }

    @Test
    void readData10000(){//2s
        long startTime = System.currentTimeMillis();    //获取开始时间

        for(int i=1; i <= 10000; i++){
            System.out.println(userMapper.queryUserById(i));
        }

        long endTime = System.currentTimeMillis();    //获取结束时间

        System.out.println("程序运行时间：" + (endTime - startTime) / 1000 + "s");    //输出程序运行时间
    }

    @Test
    void writeData200000(){//252s
        long startTime = System.currentTimeMillis();    //获取开始时间

        for(int i=0; i < 200000; i++){
            userMapper.addUserWithoutId(new User(Integer.toString(i+10), ("password" + Integer.toString(i+10)), (Integer.toString(i + 10) + "@qq.com")));
            System.out.println("i == " + i);
        }

        long endTime = System.currentTimeMillis();    //获取结束时间

        System.out.println("程序运行时间：" + (endTime - startTime) / 1000 + "s");    //输出程序运行时间
    }

    @Test
    void readData200000(){//31s
        long startTime = System.currentTimeMillis();    //获取开始时间

        for(int i=0; i < 200000; i++){
            System.out.println(userMapper.queryUserById(i));
        }

        long endTime = System.currentTimeMillis();    //获取结束时间

        System.out.println("程序运行时间：" + (endTime - startTime) / 1000 + "s");    //输出程序运行时间
    }

}
