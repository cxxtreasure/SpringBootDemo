package com.example.demo.service;

import com.example.demo.config.DbSourceFirst;
import com.example.demo.entity.Form;

import java.util.List;

public interface IFormService {
    /**
     * 获取所有
     * @return
     */
    public List<Form> findAll();
    public Form findUserById(Integer id);
    public Integer insertUser(Form form);
    public Integer updateUserById(Form form);
    public Integer deleteUserById(Integer id);
    public List<Form> findUserByUsername(String name);
}
