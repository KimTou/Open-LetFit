package com.letfit.config;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;
import com.letfit.common.DBTypeEnum;
import com.letfit.common.DynamicDataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * @author cjt
 * @date 2021/5/14 13:40
 */
@Configuration
public class DataSourceConfig {

    /**
     * 主数据库数据源，存入Spring容器
     * 注解@Primary表示主数据源
     * @return
     */
    @ConfigurationProperties(prefix = "spring.datasource.master")
    @Primary
    @Bean
    public DataSource masterDataSource(){
        return DruidDataSourceBuilder.create().build();
    }

    /**
     * 从数据库数据源，存入Spring容器
     * @return
     */
    @ConfigurationProperties(prefix = "spring.datasource.slave")
    @Bean
    public DataSource slaveDataSource(){
        return DruidDataSourceBuilder.create().build();
    }

    /**
     * 决定最终数据源
     * @param masterDataSource
     * @param slaveDataSource
     * @return
     */
    @Bean
    public DataSource targetDataSource(@Qualifier("masterDataSource") DataSource masterDataSource,
                                       @Qualifier("slaveDataSource") DataSource slaveDataSource){
        //存放主从数据源
        Map<Object,Object> targetDataSource = new HashMap<>(2);
        //主数据源
        targetDataSource.put(DBTypeEnum.MASTER, masterDataSource);
        //从数据源
        targetDataSource.put(DBTypeEnum.SLAVE, slaveDataSource);
        //实现动态切换
        DynamicDataSource dynamicDataSource = new DynamicDataSource();
        //绑定所有数据源
        dynamicDataSource.setTargetDataSources(targetDataSource);
        //设置默认数据源
        dynamicDataSource.setDefaultTargetDataSource(masterDataSource);
        return dynamicDataSource;
    }

}
