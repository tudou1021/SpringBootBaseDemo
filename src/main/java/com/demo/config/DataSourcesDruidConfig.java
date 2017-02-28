package com.demo.config;

import com.alibaba.druid.filter.stat.StatFilter;
import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;
import org.springframework.boot.context.embedded.FilterRegistrationBean;
import org.springframework.boot.context.embedded.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

/**
 * @Title:druid数据库连接池配置
 * @Description:TODO
 * @author:xu.he
 * @create:2016/12/15 上午9:53
 * @version:v1.0
 */
@Profile(value = {"dev","test"})
@Configuration
public class DataSourcesDruidConfig {

    /**
     * @Description:druid监控配置
     * @author:xu.he
     * @create:2016/12/15 上午9:56
     * @param
     */
   @Bean
    public ServletRegistrationBean druidServlet() {
        ServletRegistrationBean reg = new ServletRegistrationBean();
        reg.setServlet(new StatViewServlet());
        reg.addUrlMappings("/druid/*");
        //reg.addInitParameter("allow", "127.0.0.1");
        //reg.addInitParameter("deny","");
        reg.addInitParameter("loginUsername", "root");
        reg.addInitParameter("loginPassword", "123456");
        return reg;
    }

    /**
     * @Description:druid监控过滤
     * @author:xu.he
     * @create:2016/12/15 上午9:57
     * @param
     */
    @Bean
    public FilterRegistrationBean filterRegistrationBean() {
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
        filterRegistrationBean.setFilter(new WebStatFilter());
        filterRegistrationBean.addUrlPatterns("/*");
        filterRegistrationBean.addInitParameter("exclusions", "*.js,*.gif,*.jpg,*.png,*.css,*.ico,/druid/*");
        return filterRegistrationBean;
    }

    @Bean
    public StatFilter statFilter(){
        StatFilter statFilter=new StatFilter();
        statFilter.setLogSlowSql(true);
        statFilter.setSlowSqlMillis(10);
        statFilter.setMergeSql(true);
        return statFilter;
    }
}
