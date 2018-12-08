package com.example.demo.mapper.mybatis;

import com.example.demo.config.DbSourceFirst;
import com.example.demo.entity.User;
import com.example.demo.mapper.provider.UserProvider;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Repository;

import java.util.List;
@DbSourceFirst
@Repository
public interface UserDao {
    @SelectProvider(type = UserProvider.class,method = "GetAllUser")
    public List<User> GetAllUser();
    @SelectProvider(type = UserProvider.class,method = "GetAllUserById")
    public List<User> GetUserById(Integer id);
}
