package com.demo.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.web.WebMvcAutoConfiguration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.servlet.config.annotation.AsyncSupportConfigurer;

import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @Title:系统线程池启用TaskExecutor
 * @Description:系统启用Spring的TaskExecutor线程池
 * @author:xu.he
 * @create:2016-12-14 18:41
 * @version:v1.0
 */
@Configuration
@ConfigurationProperties(prefix = "spring.threadPool" )
public class ThreadPoolTaskExecutorConfig extends WebMvcAutoConfiguration.WebMvcAutoConfigurationAdapter{

    private Integer corePoolSize;
    private Integer maxPoolSize;
    private Integer queueCapacity;
    private Integer keepAliveSeconds;

    public Integer getCorePoolSize() {
        return corePoolSize;
    }

    public void setCorePoolSize(Integer corePoolSize) {
        this.corePoolSize = corePoolSize;
    }

    public Integer getMaxPoolSize() {
        return maxPoolSize;
    }

    public void setMaxPoolSize(Integer maxPoolSize) {
        this.maxPoolSize = maxPoolSize;
    }

    public Integer getQueueCapacity() {
        return queueCapacity;
    }

    public void setQueueCapacity(Integer queueCapacity) {
        this.queueCapacity = queueCapacity;
    }

    public Integer getKeepAliveSeconds() {
        return keepAliveSeconds;
    }

    public void setKeepAliveSeconds(Integer keepAliveSeconds) {
        this.keepAliveSeconds = keepAliveSeconds;
    }

    /**
     * 定义系统任务Task线程池
     * @return
     */
    @Bean
    public ThreadPoolTaskExecutor taskExecutor(){
        ThreadPoolTaskExecutor taskExecutor=new ThreadPoolTaskExecutor();
        //最小线程数
        taskExecutor.setCorePoolSize(corePoolSize);
        //最大线程数
        taskExecutor.setMaxPoolSize(maxPoolSize);
        //线程队列长度
        taskExecutor.setQueueCapacity(queueCapacity);
        //线程存活时间
        taskExecutor.setKeepAliveSeconds(keepAliveSeconds);
        //线程池饱和策略
        taskExecutor.setRejectedExecutionHandler(new RejectedExecutionHandler() {
            @Override
            public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
                ThreadPoolExecutor.CallerRunsPolicy callerRunsPolicy=new ThreadPoolExecutor.CallerRunsPolicy();
                callerRunsPolicy.rejectedExecution(r,executor);
            }
        });
        return taskExecutor;
    }

    @Override
    public void configureAsyncSupport(AsyncSupportConfigurer configurer) {
        configurer.setTaskExecutor(taskExecutor());
    }
}