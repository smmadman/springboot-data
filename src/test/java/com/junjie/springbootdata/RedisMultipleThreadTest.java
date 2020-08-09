package com.junjie.springbootdata;

import com.junjie.springbootdata.service.CustomMultiThreadingService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class RedisMultipleThreadTest {

    @Autowired
    CustomMultiThreadingService customMultiThreadingService;

    @Test
    public void writeTest(){
        customMultiThreadingService.executeWriteRedisKeys();
        while(true);
    }

    @Test
    public void showTest() {
        customMultiThreadingService.executeShowRedisKeys();
        while(true);
    }

    @Test
    public void readTest() throws InterruptedException {
        while(true){
            customMultiThreadingService.executeReadRedisKeys();
            Thread.sleep(1000);
        }
    }
}
