package com.demo.controller;

import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.WebAsyncTask;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;

/**
 * @Title:异步Controller
 * @Description:异步处理Controller的demo
 * @author:xu.he
 * @version:v1.0
 */
@RestController
@RequestMapping("/user")
public class AsyncController {

    private Logger logger=Logger.getLogger(AsyncController.class);

    @RequestMapping("/validate")
    public @ResponseBody WebAsyncTask validateToken(HttpServletRequest request, HttpServletResponse response){
        Callable callable=new Callable() {
            @Override
            public Object call() throws Exception {
                Map<String,Object> retMap=new HashMap<>();
                retMap.put("result","true");
                retMap.put("msg","操作成功！");
                Long threadId=Thread.currentThread().getId();
                String threadName=Thread.currentThread().getName();
                logger.info(" current thread id is "+threadId+" name is "+threadName);
                if(threadId.intValue()%10==0){
                    Thread.currentThread().sleep(3000);
                }
                return retMap;
            }
        };

        WebAsyncTask webAsyncTask=new WebAsyncTask(200,callable);
        webAsyncTask.onTimeout(new Callable() {
            @Override
            public Object call() throws Exception {
                Map<String,Object> retMap=new HashMap<>();
                retMap.put("result","failed");
                retMap.put("msg","请求超时！");
                return retMap;
            }
        });

        return webAsyncTask;
    }
}
