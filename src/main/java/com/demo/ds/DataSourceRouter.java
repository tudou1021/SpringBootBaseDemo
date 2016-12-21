package com.demo.ds;

import org.apache.log4j.Logger;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Title:数据源路由器
 * @Description:TODO
 * @author:xu.he
 * @create:2016/12/15 上午10:45
 * @version:v1.0
 */
public class DataSourceRouter extends AbstractRoutingDataSource {

    private Logger logger=Logger.getLogger(DataSourceDynamicAop.class);

    private final int dataSourceNumber;

    private AtomicInteger count = new AtomicInteger(0);

    public DataSourceRouter(int dataSourceNumber) {
        this.dataSourceNumber = dataSourceNumber;
    }

    @Override
    protected Object determineCurrentLookupKey() {
        String typeKey = DataSourceContextHolder.getJdbcType();
        if(DataSourceType.WRITE.name().equals(typeKey)){
            logger.info("{DataSourceRouter set write key !!!!}");
            return DataSourceType.WRITE.name();
        }
        //避免number出现负数，incr当到达Integer最大值后，再递增会出现负数
        int number=count.incrementAndGet()&Integer.MAX_VALUE;
        int lookUpKey=number % dataSourceNumber;
        logger.info("{DataSourceRouter set read{"+lookUpKey+"} key !!!!}");
        return new Integer(lookUpKey);
    }
}
