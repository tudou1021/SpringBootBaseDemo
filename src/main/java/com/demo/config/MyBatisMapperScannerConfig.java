package com.demo.config;

import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/**
 * @Title:MyBatis自动扫描管理类
 * @Description:TODO
 * @author:xu.he
 * @create:2016/12/15 上午10:45
 * @version:v1.0
 */
@Configuration
@AutoConfigureAfter(MybatisConfiguration.class)
public class MyBatisMapperScannerConfig {

    @Bean
    public MapperScannerConfigurer mapperScannerConfigurer() {
        MapperScannerConfigurer mapperScannerConfigurer = new MapperScannerConfigurer();
        mapperScannerConfigurer.setSqlSessionFactoryBeanName("sqlSessionFactory");
        mapperScannerConfigurer.setBasePackage("com.demo.mapper");
        return mapperScannerConfigurer;
    }
}
