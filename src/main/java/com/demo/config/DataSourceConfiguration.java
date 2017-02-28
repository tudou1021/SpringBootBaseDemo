package com.demo.config;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;

/**
 * @Title:数据源信息管理器
 * @Description:TODO
 * @author:xu.he
 * @create:2016/12/15 上午10:48
 * @version:v1.0
 */
@Configuration
public class DataSourceConfiguration {

    private Logger logger=Logger.getLogger(DataSourceConfiguration.class);

    //加载数据源
    @Value("${datasource.type}")
    private Class<? extends DataSource> dataSourceType;

    //主库数据源（写数据源）
    @Primary
    @Bean(name="writeDataSource")
    @ConfigurationProperties(prefix = "datasource.write")
    public DataSource writeDataSource(){
        DataSource writeDataSource=DataSourceBuilder.create().type(dataSourceType).build();
        return writeDataSource;
    }

    //读库数据源01
    @Bean(name="readDataSource1")
    @ConfigurationProperties(prefix = "datasource.read1")
    public DataSource readDataSource01(){
        return DataSourceBuilder.create().type(dataSourceType).build();
    }

    //读库数据源02
    @Bean(name="readDataSource2")
    @ConfigurationProperties(prefix = "datasource.read2")
    public DataSource readDataSource02(){
        return DataSourceBuilder.create().type(dataSourceType).build();
    }

}
