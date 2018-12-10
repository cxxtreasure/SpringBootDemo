package com.example.demo.mapper2.mybatis;

import com.example.demo.config.DbSourceSecond;
import com.example.demo.entity.News;
import com.example.demo.mapper2.provider.NewsProvider;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.SelectProvider;
import org.springframework.stereotype.Repository;

@Mapper
@DbSourceSecond
public interface NewsDao {
    @SelectProvider(type = NewsProvider.class,method = "GetTop1News")
    public News GetTop1News();
}
