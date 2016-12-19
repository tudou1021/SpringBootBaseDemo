package com.demo.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.Profile;

/**
 * @Title:Dubbo配置加载类
 * @Description:TODO
 * @author:xu.he
 * @create:2016-12-18 10:38
 * @version:v1.0
 */
@Configuration
public class DubboConfig{

    @Profile("dev")
    @ImportResource("classpath*:dubbo-dev.xml")
    static class loadDev{}

    @Profile("test")
    @ImportResource("classpath*:dubbo-test.xml")
    static class loadTest{}

    @Profile("online")
    @ImportResource("classpath*:dubbo-online.xml")
    static class loadOnline{}
}
