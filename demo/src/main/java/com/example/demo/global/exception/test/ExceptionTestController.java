package com.example.demo.global.exception.test;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ExceptionTestController {
    @RequestMapping(value = "ExceptionTest",method = RequestMethod.GET)
    public String index(){
        String str=null;
        str.split("1");
        return "ExceptionTestController";
    }
}
