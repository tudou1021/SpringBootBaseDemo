package com.demo.config;

import com.demo.BeanContext;
import com.demo.ds.DataSourceRouter;
import com.demo.ds.DataSourceType;
import com.github.pagehelper.PageHelper;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.boot.autoconfigure.MybatisAutoConfiguration;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * @Title:Mybatis配置管理类
 * @Description:TODO
 * @author:xu.he
 * @create:2016/12/15 上午10:44
 * @version:v1.0
 */
@Configuration
@AutoConfigureAfter(DataSourceConfiguration.class)
public class MybatisConfiguration extends MybatisAutoConfiguration {

    @Value("${datasource.readSize}")
    private String dataSourceSize;

    @Bean(name = "sqlSessionFactory")
    @Override
    public SqlSessionFactory sqlSessionFactory(DataSource dataSource) throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(roundRobinDataSource());
        bean.setTypeAliasesPackage("com.demo.model");

        //分页插件,插件无非是设置mybatis的拦截器
        PageHelper pageHelper = new PageHelper();
        Properties properties = new Properties();
        properties.setProperty("reasonable", "true");
        properties.setProperty("supportMethodsArguments", "true");
        properties.setProperty("returnPageInfo", "check");
        properties.setProperty("params", "count=countSql");
        pageHelper.setProperties(properties);

        //添加插件
        bean.setPlugins(new Interceptor[]{pageHelper});

        //添加XML目录
        ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        try {
            bean.setMapperLocations(resolver.getResources("classpath:mapper/*.xml"));
            return bean.getObject();
        } catch (Exception e) {
            throw new RuntimeException("sqlSessionFactory init fail",e);
        }
    }

    @Bean(name = "sqlSessionTemplate")
    @Override
    public SqlSessionTemplate sqlSessionTemplate(SqlSessionFactory sqlSessionFactory) {
        return new SqlSessionTemplate(sqlSessionFactory);
    }

    /**
     * 有多少个数据源就要配置多少个bean
     * write 数据源返回name
     * read 数据源返回index
     * @return
     */
    @Bean
    public AbstractRoutingDataSource roundRobinDataSource() {
        int size = Integer.parseInt(dataSourceSize);
        DataSourceRouter dataSourceRouter = new DataSourceRouter(size);
        Map<Object, Object> targetDataSources = new HashMap<Object, Object>();
        DataSource writeDataSource = (DataSource) BeanContext.getBean("writeDataSource");
        // 写
        targetDataSources.put(DataSourceType.WRITE.name(), BeanContext.getBean("writeDataSource"));

        for (int i = 0; i < size; i++) {
            targetDataSources.put(i, BeanContext.getBean("readDataSource" + (i + 1)));
        }
        dataSourceRouter.setDefaultTargetDataSource(writeDataSource);
        dataSourceRouter.setTargetDataSources(targetDataSources);
        return dataSourceRouter;
    }
}
