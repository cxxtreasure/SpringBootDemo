package com.example.demo.config;

import com.alibaba.druid.pool.DruidDataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
@Component
public abstract class AbstractDbConfig {
    protected abstract SqlSessionFactory sqlSessionFactory(DataSource dataSource, String mapperLocation) throws Exception;

    protected abstract DataSource dataSourceFactory(String driveClassName, String url, String userName, String password);
}
