package com.example.demo.service.Impl;

import com.example.demo.entity.News;
import com.example.demo.mapper2.mybatis.NewsDao;
import com.example.demo.mapper2.NewsMapper;
import com.example.demo.service.INewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class NewsService implements INewsService{
    @Autowired
    private NewsMapper newsMapper;
    @Autowired
    private NewsDao newsDao;
    @Override
    public List<News> findAll() {
        return newsMapper.findAll();
    }
    @Override
    public News GetTop1News() {
        return newsDao.GetTop1News();
    }
}
