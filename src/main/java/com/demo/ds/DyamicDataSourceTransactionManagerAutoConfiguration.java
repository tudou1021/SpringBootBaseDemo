package com.demo.ds;

import com.demo.BeanContext;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

@Configuration
@EnableTransactionManagement
public class DyamicDataSourceTransactionManagerAutoConfiguration extends DataSourceTransactionManagerAutoConfiguration {
    @Bean(name = "transactionManager")
    @Override
    public DataSourceTransactionManager transactionManager() {
        return new DataSourceTransactionManager((DataSource) BeanContext.getBean("roundRobinDataSouce"));
    }
}
