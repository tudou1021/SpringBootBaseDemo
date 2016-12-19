package com.demo.ds;

import org.apache.log4j.Logger;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

/**
 * @Title:数据源动态拦截器
 * @Description:根据ServiceName匹配方法名称设置数据源
 * @author:xu.he
 * @create:2016/12/15 上午9:59
 * @version:v1.0
 */
@Component
@Aspect
public class DataSourceDynamicAop {

    private Logger logger=Logger.getLogger(DataSourceDynamicAop.class);

    @Before("execution(* com.demo.service.*.query*(..)) " +
            "|| execution(* com.demo.service.*.get*(..)) " +
            "|| execution(* com.demo.service.*.select*(..)) " +
            "|| execution(* com.demo.service.*.load*(..)) " +
            "|| execution(* com.demo.service.*.count*(..)) " +
            "|| execution(* com.demo.service.*.check*(..)) " +
            "|| execution(* com.demo.service.*.list*(..)) " )
    public void setReadDataSource(){
        DataSourceContextHolder.read();
    }

    @Before("execution(* com.demo.service.*.update*(..)) " +
            "|| execution(* com.demo.service.*.insert*(..)) " +
            "|| execution(* com.demo.service.*.add*(..)) " +
            "|| execution(* com.demo.service.*.save*(..)) " +
            "|| execution(* com.demo.service.*.create*(..)) " +
            "|| execution(* com.demo.service.*.edit*(..)) " +
            "|| execution(* com.demo.service.*.remove*(..)) " +
            "|| execution(* com.demo.service.*.delete*(..))")
    public void setWriteDataSource(){
        DataSourceContextHolder.write();
    }

}
