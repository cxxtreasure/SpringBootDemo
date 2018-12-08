package com.example.demo.mapper2.provider;

public class NewsProvider {
    public String GetTop1News(){
        return "select  * from webdb.news limit 1 ";
    }
}
