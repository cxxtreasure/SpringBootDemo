package com.example.demo.mapper.provider;

import org.apache.ibatis.jdbc.SQL;

public class UserProvider {
    public String GetAllUser(){
        return " select * from db_person.user ";
    }
    public String GetAllUserById(Integer id){
        return " select * from db_person.user where  id=#{id}";
        //参数化
//        return new SQL(){
//            {
//                SELECT("*");
//                FROM("user");
//                WHERE("id=#{id}");
//            }
//        }.toString();
    }
}
