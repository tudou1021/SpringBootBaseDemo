package com.demo.ds;

/**
 * @Title:数据源标识管理器
 * @Description:TODO
 * @author:xu.he
 * @create:2016/12/15 上午10:46
 * @version:v1.0
 */
public class DataSourceContextHolder {

    private static final ThreadLocal<String> local = new ThreadLocal<String>();

    public static ThreadLocal<String> getLocal() {
        return local;
    }

    /**
     * 读可能是多个库
     */
    public static void read() {
        local.set(DataSourceType.READ.name());
    }

    /**
     * 写只有一个库
     */
    public static void write() {
        local.set(DataSourceType.WRITE.name());
    }

    public static String getJdbcType() {
        return local.get();
    }
}
