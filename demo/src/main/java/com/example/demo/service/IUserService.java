package com.example.demo.service;

import com.example.demo.config.DbSourceFirst;
import com.example.demo.entity.User;

import java.util.List;

public interface IUserService {
    public List<User> GetAllUser();
    public List<User> GetUserById(Integer id);
}
