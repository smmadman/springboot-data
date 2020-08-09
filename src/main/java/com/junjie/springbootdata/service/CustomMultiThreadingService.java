package com.junjie.springbootdata.service;

import com.junjie.springbootdata.mapper.UserMapper;
import com.junjie.springbootdata.pojo.User;
import com.junjie.springbootdata.utils.RedisUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomMultiThreadingService {

    @Autowired
    UserMapper userMapper;

    @Autowired
    RedisUtil redisUtil;


    private Logger logger = LoggerFactory.getLogger(CustomMultiThreadingService.class);

    /**
     * @Description:通过@Async注解表明该方法是一个异步方法，
     * 如果注解在类级别上，则表明该类所有的方法都是异步方法，而这里的方法自动被注入使用ThreadPoolTaskExecutor作为TaskExecutor
     * @Title: executeAysncTask1
     * @Date: 2018年9月21日 下午2:54:32
     * @Author: OnlyMate
     * @Throws
     * @param i
     */
    @Async
    public void executeAysncTask1(Integer i){
//        logger.info("CustomMultiThreadingService ==> executeAysncTask1 method: 执行异步任务{} ", i);
        logger.info("线程" + Thread.currentThread().getName() + " 执行异步任务：" + i);
    }

    @Async
    public void executeShowRedisKeys(){
        while(true){
//            System.out.println(redisUtil.lGetListSize("Username"));
            if (redisUtil.lGetListSize("Username") == 0){
                continue;
            }else{
                System.out.println(Thread.currentThread().getName() + " : show Redis cache. " +
                        "Username list length is " + redisUtil.lGetListSize("Username") + ".");
            }
        }
    }

    @Async
    public void executeReadRedisKeys() throws InterruptedException {
//        Object username = redisUtil.lpop("Username");
//        if(username != null){
//            String uname = (String) username;
//            System.out.println(userMapper.queryUserByName(uname));
//        }
        System.out.println("11111");
    }

    @Async
    public void executeWriteRedisKeys(){
        for(int i = 1; i <= userMapper.countLines(); i++){
            User user = userMapper.queryUserById(i);
            String name = user.getUsername();

            redisUtil.lSet("Username", name);
            System.out.println(Thread.currentThread().getName() + ": Write " +
                    name + " to RedisCache. " + "Excuted " + i + " times");
        }
    }

}
