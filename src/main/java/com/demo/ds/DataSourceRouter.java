package com.demo.ds;

import org.apache.log4j.Logger;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by tudoubig on 2016/12/9.
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
        int number=count.incrementAndGet();
        int lookUpKey=number % dataSourceNumber;
        logger.info("{DataSourceRouter set read{"+lookUpKey+"} key !!!!}");
        return new Integer(lookUpKey);
    }
}
