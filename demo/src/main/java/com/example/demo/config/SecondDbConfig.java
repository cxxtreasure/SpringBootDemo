package com.example.demo.config;

import com.alibaba.druid.pool.DruidDataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
@Configuration
@MapperScan(basePackages = {"com.example.demo.mapper2"},annotationClass = DbSourceSecond.class,
        sqlSessionTemplateRef = "DbSourceSecondTemplate")
public class SecondDbConfig {
    @Value("${spring.datasource.second.url}")
    private String url;

    @Value("${spring.datasource.second.username}")
    private String userName;

    @Value("${spring.datasource.second.password}")
    private String password;

    @Value("${spring.datasource.second.driver-class-name}")
    private String driveClassName;

    @Value(value = "${mybatis.mapper-locations}")
    private String mapperLocation;


    @Bean(name = "dbSecond")
    public DataSource secondaryDataSource() {
        return dataSourceFactory(driveClassName, url, userName, password);
    }

    public DataSource dataSourceFactory(String driveClassName, String url, String userName, String password) {
        DruidDataSource datasource = new DruidDataSource();
        datasource.setDriverClassName(driveClassName);
        datasource.setUrl(url);
        datasource.setUsername(userName);
        datasource.setPassword(password);
        datasource.setMaxActive(20);
        datasource.setInitialSize(20);
        return datasource;
    }

    @Bean(name = "DbSourceSecondTemplate")
    public SqlSessionTemplate dbSecondSqlTemplate() throws Exception {
        return new SqlSessionTemplate((sqlSessionFactory(secondaryDataSource(), mapperLocation)));
    }

    public SqlSessionFactory sqlSessionFactory(DataSource dataSource, String mapperLocation) throws Exception{
        SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
        factoryBean.setDataSource(dataSource);
        ResourcePatternResolver resourceResolver = new PathMatchingResourcePatternResolver();
        Resource[] resource= resourceResolver.getResources(mapperLocation);
        factoryBean.setMapperLocations(resource);
        return factoryBean.getObject();
    }

    @Bean
    @Qualifier("dbSecondTransaction")
    public PlatformTransactionManager demoUserTransaction() {
        return new DataSourceTransactionManager(secondaryDataSource());
    }
}
