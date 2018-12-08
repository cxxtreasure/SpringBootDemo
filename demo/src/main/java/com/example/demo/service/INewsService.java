package com.example.demo.service;

import com.example.demo.entity.News;

import java.util.List;

public interface INewsService {
    public List<News> findAll();
    public News GetTop1News();
}
