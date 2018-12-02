package com.example.demo.controller;

import com.alibaba.fastjson.JSONObject;
import com.example.demo.entity.Form;
import com.example.demo.service.IFormService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

@RestController
public class FormController {
    @Autowired
    private IFormService iFormService;
    @RequestMapping(value = "form",method = RequestMethod.GET)
    public String form1(){
        List<Form> list= iFormService.findAll();
        String json=JSONObject.toJSON(list).toString();
        System.out.print(json);
        return  json;
    }
    @RequestMapping(value = "getUserById",method = RequestMethod.GET)
    public String getUserById(){
        Form form= iFormService.findUserById(1);
        String json=JSONObject.toJSON(form).toString();
        System.out.print(json);
        return  json;
    }
    @RequestMapping(value = "insertUser",method = RequestMethod.GET)
    public int insertUser(){
        Form form = new Form();
        form.setName("何炅");
        form.setCreate_date(new Date());
        form.setMoney(100000.12);
        form.setModify_date(new Date());
        form.setIs_valid(1);
        Integer count= iFormService.insertUser(form);
        System.out.print(count);
        return  count;
    }
    @RequestMapping(value = "updateUser",method = RequestMethod.GET)
    public int updateUser(){
        Form form = new Form();
        form.setName("曹行行");
        form.setId(1);
        Integer count= iFormService.updateUserById(form);
        System.out.print(count);
        return  count;
    }
    @RequestMapping(value = "deleteUser",method = RequestMethod.GET)
    public int deleteUser(){
        Integer count= iFormService.deleteUserById(1);
        System.out.print(count);
        return  count;
    }
    @RequestMapping(value = "findUserByUsername",method = RequestMethod.GET)
    public String findUserByUsername(){
        List<Form> form= iFormService.findUserByUsername("曹");
        String json=JSONObject.toJSON(form).toString();
        System.out.print(json);
        return  json;
    }
}
