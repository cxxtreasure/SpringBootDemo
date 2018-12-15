package com.example.demo.mapper2;

import com.example.demo.config.DbSourceSecond;
import com.example.demo.entity.News;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;
@Mapper
@DbSourceSecond
public interface NewsMapper {
    public List<News> findAll();
}
