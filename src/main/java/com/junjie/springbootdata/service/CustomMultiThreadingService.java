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

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class CustomMultiThreadingService {

    @Autowired
    UserService userService;

    @Autowired
    RedisUtil redisUtil;

    String setName = "AllPages";


    private Logger logger = LoggerFactory.getLogger(CustomMultiThreadingService.class);

    /**
     * 异步进程测试
     * 如果注解在类级别上，则表明该类所有的方法都是异步方法，而这里的方法自动被注入使用ThreadPoolTaskExecutor作为TaskExecutor
     */
    @Async
    public void executeAysncTask1(Integer i){
//        logger.info("CustomMultiThreadingService ==> executeAysncTask1 method: 执行异步任务{} ", i);
        logger.info("线程" + Thread.currentThread().getName() + " 执行异步任务：" + i);
    }

    /**
     * 异步单进程：持续监测set中的元素个数
     * @throws InterruptedException
     */
    @Async
    public void executeShowRedisSetKeys() throws InterruptedException {
        while(true){
            if (redisUtil.sGetSetSize(setName) == 0){
                System.out.println("当前set中没有元素");
            }else{
                Set hashSet = redisUtil.smembers(setName);
                System.out.println(Thread.currentThread().getName() + " : show Redis set cache. " +
                        "Set<AllPages>'s length is " + redisUtil.sGetSetSize(setName) + ".");
            }
            Thread.sleep(1000);
        }
    }

    /**
     * 异步多线程：随机抽取一页并且将页中所有id查询出对应的email并打印
     * @throws InterruptedException
     */
    @Async
    public void executeReadRedisKeys() throws InterruptedException {

        System.out.println(Thread.currentThread().getName() + "Started!");
        while (true) {
            if(redisUtil.sGetSetSize(setName) == 0){ // 如果set AllPage中无值，则等待
                System.out.println(Thread.currentThread().getName() + "Sleeping-------");
                Thread.sleep(200);
            }else{ // 若有值
                System.out.println(Thread.currentThread().getName() + "Working!!!-------");
                String curPage = (String) redisUtil.spop(setName); //随机pop出一页

                while(redisUtil.lGetListSize(curPage) != 0){
                    System.out.println(Thread.currentThread().getName() + "Querying!!!------- ListSize: " +
                            redisUtil.lGetListSize(curPage));
                    int id = (Integer) redisUtil.lpop(curPage);
                    System.out.println("Popped id is :" + id);

                    User user = userService.queryUserById(id);
                    System.out.print(user.getEmail() + " ");
                }
            }
        }
    }

    /**
     * 异步单进程：读取mysql表中id值并分n页送入redis中
     * 首先将第n页中的id值写入一个list，并命名为 pn
     * 之后将 pn 写入一个大set中，此set保存所有页的名称索引
     */
    @Async
    public void executeWriteRedisKeys(){ //

        List<List<Integer>> allIndex = userService.pagesIndex(1000);

        int pageCount = allIndex.size();//页数

        for(int i = 0; i < pageCount; i++){//循环将X页的id放入名为“pX”的list中，并且将“pX”放入总set AllPages中
            List<Integer> currentPage = allIndex.get(i);
            String curPageName = "p" + (Integer.toString(i));

            Integer[] curPageArray = new Integer[currentPage.size()];
            currentPage.toArray(curPageArray); //转换成数组

            if(redisUtil.lSetList(curPageName, curPageArray)){
                System.out.println("当前页id写入list成功：" + curPageName);
                if(redisUtil.sSet(setName, curPageName) > 0){
                    System.out.println("当前页写入allPageSet成功" + curPageName);
                }
            }else{
                System.out.println("当前页id写入list失败：" + curPageName);
            }
        }
    }
}
