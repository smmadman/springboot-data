package com.junjie.springbootdata.controller;

import com.junjie.springbootdata.service.CustomMultiThreadingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "/mT")
public class CustomMultiThreadingController {

    @Autowired
    private CustomMultiThreadingService customMultiThreadingService;

    @ResponseBody
    @RequestMapping(value="/dotask")
    public String doTask(){
        for (int  i = 0; i < 10; i++){
            customMultiThreadingService.executeAysncTask1(i);
//            customMultiThreadingService.executeAsyncTask2(i);
        }

        return "success";
    }
}
