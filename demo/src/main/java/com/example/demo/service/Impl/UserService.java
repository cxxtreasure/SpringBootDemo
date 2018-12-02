package com.example.demo.service.Impl;

import com.example.demo.entity.User;
import com.example.demo.mapper.mybatis.UserDao;
import com.example.demo.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService implements IUserService{
    @Autowired
    private UserDao userDao;
    @Override
    public List<User> GetAllUser() {
        return userDao.GetAllUser();
    }

    @Override
    public List<User> GetUserById(Integer id) {
        return userDao.GetUserById(id);
    }
}
