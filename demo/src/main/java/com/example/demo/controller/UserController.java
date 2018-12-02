package com.example.demo.controller;

import com.alibaba.fastjson.JSONObject;
import com.example.demo.entity.User;
import com.example.demo.service.IUserService;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserController {
    @Autowired
    private IUserService iUserService;

    @RequestMapping(value = "GetUserList",method = RequestMethod.GET)
    public String GetUserList(){
        List<User> list= iUserService.GetAllUser();
        String json= JSONObject.toJSON(list).toString();
        System.out.print(json);
        return  json;
    }
    @RequestMapping(value = "GetUserById",method = RequestMethod.GET)
    public String GetUserById(Integer id){
        List<User> list= iUserService.GetUserById(id);
        String json= JSONObject.toJSON(list).toString();
        System.out.print(json);
        return  json;
    }
}
