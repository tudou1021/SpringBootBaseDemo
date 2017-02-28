package com.demo.config;

import com.demo.filter.HttpRequestFilter;
import com.demo.inteceptor.HttpRequestInteceptor;
import org.springframework.boot.autoconfigure.web.WebMvcAutoConfiguration;
import org.springframework.boot.context.embedded.FilterRegistrationBean;
import org.springframework.boot.context.embedded.ServletListenerRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;

import java.util.ArrayList;
import java.util.List;

/**
 * @Title:过滤器和拦截器配置
 * @Description:TODO
 * @author:xu.he
 * @create:2016-12-22 10:23
 * @version:v1.0
 */
@Configuration
public class WebConfig extends WebMvcAutoConfiguration.WebMvcAutoConfigurationAdapter{

    /**
     * 配置过滤器
     * @return
     */
    @Bean
    public FilterRegistrationBean filterRegistrationBean(){
        FilterRegistrationBean filterRegistrationBean=new FilterRegistrationBean();
        filterRegistrationBean.setName("httpFilter");
        filterRegistrationBean.setFilter(new HttpRequestFilter());
        List<String> urlList = new ArrayList<>();
        urlList.add("/*");
        filterRegistrationBean.setUrlPatterns(urlList);
        return filterRegistrationBean;
    }

//    @Bean
    public ServletListenerRegistrationBean servletListenerRegistrationBean(){
        ServletListenerRegistrationBean listenerRegistrationBean=new ServletListenerRegistrationBean();
//        listenerRegistrationBean.setListener();
        return listenerRegistrationBean;
    }

    /**
     * 配置拦截器
     * @author lance
     * @param registry
     */
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new HttpRequestInteceptor()).addPathPatterns("/user/*");
    }
}
