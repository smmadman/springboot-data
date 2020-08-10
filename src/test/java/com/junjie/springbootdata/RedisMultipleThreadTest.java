package com.junjie.springbootdata;

import com.junjie.springbootdata.service.CustomMultiThreadingService;
import com.junjie.springbootdata.utils.RedisUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class RedisMultipleThreadTest {

    @Autowired
    CustomMultiThreadingService customMultiThreadingService;

    @Autowired
    RedisUtil redisUtil;

    @Test
    public void writeTest(){
        customMultiThreadingService.executeWriteRedisKeys();
        while(true);
    }

    @Test
    public void showTest() throws InterruptedException {
        customMultiThreadingService.executeShowRedisSetKeys();
        while(true);
    }

    @Test
    public void readTest() throws InterruptedException {
        customMultiThreadingService.executeReadRedisKeys();
        customMultiThreadingService.executeReadRedisKeys();
        customMultiThreadingService.executeReadRedisKeys();
//        customMultiThreadingService.executeReadRedisKeys();
//        customMultiThreadingService.executeReadRedisKeys();
        while(true);
    }

}
