# SpringBootBaseDemo
基于spring-boot 1.3.2版本

1、整合Mybaties，实现基于Mapper.xml文件管理SQL

2、基于AOP实现数据源读写分离，数据源使用阿里Druid

3、实现环境区分配置（dev、test、online）

4、集成redis，添加redis缓存管理类及redis工具类

5、集成Swagger插件，默认只在dev和test环境下可用。项目启动后访问：localhost:8080/swagger-ui.html

6、集成热部署插件，添加使用Spring的TaskExecutor线程池，添加异步Controller的示例

7、整合事务管理器和事务拦截器，使用AOP代理目标类，执行相应拦截器