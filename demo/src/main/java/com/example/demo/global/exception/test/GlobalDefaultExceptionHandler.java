package com.example.demo.global.exception.test;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@ControllerAdvice
//如果返回的为json数据或其他对象，添加该注解
@ResponseBody
public class GlobalDefaultExceptionHandler {
    public static final String DEFAULT_ERROR_VIEW="error";
    @ExceptionHandler({NullPointerException.class,NumberFormatException.class})
    public ModelAndView formatErrorHandler(HttpServletRequest request,Exception ex){
        System.out.println("已经捕获到异常");
        ModelAndView mv=new ModelAndView();
        mv.addObject("exception",ex);
        mv.addObject("timeStamp",new Date());
        mv.setViewName(DEFAULT_ERROR_VIEW);
        return mv;
    }
}
