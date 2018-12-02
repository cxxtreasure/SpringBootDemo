package com.example.demo.mapper;

import com.example.demo.entity.Form;
import org.springframework.stereotype.Repository;

import java.util.List;
/*
这里是映射过来的
@Service用于标注业务层组件
@Controller用于标注控制层组件（如struts中的action）
@Repository用于标注数据访问组件，即DAO组件
@Component泛指组件，当组件不好归类的时候，我们可以使用这个注解进行标注。
 */
@Repository
public interface FormMapper {
    public List<Form> findAll();
    public Form findUserById(Integer id);
    public Integer insertUser(Form form);
    public Integer updateUserById(Form form);
    public Integer deleteUserById(Integer id);
    public List<Form> findUserByUsername(String name);
}
