package com.demo.ds;

import com.demo.BeanContext;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

/**
 * @Title:数据源事务管理器
 * @Description:TODO
 * @author:xu.he
 * @create:2016/12/15 上午10:01
 * @version:v1.0
 */
@Configuration
@EnableTransactionManagement
public class DyamicDataSourceTransactionManagerAutoConfiguration extends DataSourceTransactionManagerAutoConfiguration {
    @Bean(name = "transactionManager")
    @Override
    public DataSourceTransactionManager transactionManager() {
        return new DataSourceTransactionManager((DataSource) BeanContext.getBean("roundRobinDataSouce"));
    }
}
