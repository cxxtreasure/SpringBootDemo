package com.example.demo.service;


import com.example.demo.mapper.FormMapper;
import com.example.demo.entity.Form;
import com.github.pagehelper.PageHelper;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class FormService implements IFormService {
    @Autowired
    private FormMapper formMapper;
    //private SqlSession sqlSession;

//    public FormService(SqlSession sqlSession) {
//        this.sqlSession = sqlSession;
//    }

    @Override
    public List<Form> findAll() {
        PageHelper.startPage(1, 1);
        return formMapper.findAll();
        //return sqlSession.selectList("findAll");
    }

    @Override
    public Form findUserById(Integer id) {
        return formMapper.findUserById(id);
    }

    @Override
    public Integer insertUser(Form form) {
        return formMapper.insertUser(form);
    }

    @Override
    public Integer updateUserById(Form form) {
        return formMapper.updateUserById(form);
    }

    @Override
    public Integer deleteUserById(Integer id) {
        return formMapper.deleteUserById(id);
    }

    @Override
    public List<Form> findUserByUsername(String name) {
        return formMapper.findUserByUsername(name);
    }
}
