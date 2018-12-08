package com.example.demo.controller;

import com.alibaba.fastjson.JSONObject;
import com.example.demo.entity.News;
import com.example.demo.service.INewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class NewsController {
    @Autowired
    private INewsService iNewsService;
    @GetMapping(value = "GetNewsList")
    public String GetNewsList(){
        List<News> list = iNewsService.findAll();
        String json= JSONObject.toJSON(list).toString();
        System.out.print(json);
        return  json;
    }
    @GetMapping(value = "GetTop1News")
    public String GetTop1News(){
        News news= iNewsService.GetTop1News();
        String json= JSONObject.toJSON(news).toString();
        System.out.print(json);
        return  json;
    }
}
