package com.example.demo.controller;

import com.example.demo.entity.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PersonController {
    @Autowired
    private Person person;
    @GetMapping(value = "GetPersonInfo")
    public String GetPersonInfo(){
        return person.toString();
    }
}
