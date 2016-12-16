package com.demo.ds;

import com.demo.BeanContext;
import org.springframework.aop.framework.autoproxy.BeanNameAutoProxyCreator;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.interceptor.NameMatchTransactionAttributeSource;
import org.springframework.transaction.interceptor.TransactionInterceptor;

import javax.sql.DataSource;
import java.util.List;
import java.util.Properties;

/**
 * @Title:数据源事务拦截器
 * @Description:TODO
 * @author:xu.he
 * @create:2016-12-15 18:00
 * @version:v1.0
 */
@Configuration
@EnableTransactionManagement
@ConfigurationProperties(prefix = "transaction")
public class DataSourceTransactionInterceptor {


    private List<DataSourceTransactionAttribute.DSAttribute> list;

    public void setList(List<DataSourceTransactionAttribute.DSAttribute> list) {
        this.list = list;
    }

    public List<DataSourceTransactionAttribute.DSAttribute> getList() {
        return list;
    }

    /**
     * 事务管理器配置
     * @return
     */
    @Bean(name = "transactionManager")
    public DataSourceTransactionManager transactionManager(){
        return new DataSourceTransactionManager((DataSource) BeanContext.getBean("roundRobinDataSource"));
    }

    /**
     * 事务名称匹配属性源
     * @return
     */
    @Bean(name = "txAttributeSource")
    public NameMatchTransactionAttributeSource transactionAttributeSource(){
        NameMatchTransactionAttributeSource attributeSource=new NameMatchTransactionAttributeSource();
        Properties properties=new Properties();
        for (DataSourceTransactionAttribute.DSAttribute dsAttribute : getList()) {
            properties.setProperty(dsAttribute.getMatchName(), dsAttribute.getRule());
        }
        attributeSource.setProperties(properties);
        return attributeSource;
    }

    /**
     * 事务拦截器
     * @return
     */
    @Bean(name = "txInterceptor")
    public TransactionInterceptor transactionInterceptor(){
        TransactionInterceptor transactionInterceptor=new TransactionInterceptor();
        transactionInterceptor.setTransactionManager(transactionManager());
        transactionInterceptor.setTransactionAttributeSource(transactionAttributeSource());
        return transactionInterceptor;
    }

    /**
     * 针对BeanName进行事务拦截
     * @return
     */
    @Bean
    public BeanNameAutoProxyCreator beanNameAutoProxyCreator(){
        BeanNameAutoProxyCreator creator=new BeanNameAutoProxyCreator();
        creator.setBeanNames(new String[]{"userService"});
        creator.setInterceptorNames("txInterceptor");
        return creator;
    }

}
