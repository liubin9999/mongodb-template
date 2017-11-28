package com.example.demo.service;

import com.example.demo.domain.User;
import com.example.demo.domain.UserTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

/**
 * mongoTemplate
 *
 * @author orange
 * @Time 2017/11/27 0027
 */
@Service
public class UserTemplateService {

    @Autowired
    private UserTemplate userTemplate;


    public void save() {
        User user = new User();
        user.createUserCase();
        userTemplate.save(user);
    }

    public void repalce() {
        User user = userTemplate.findById("5a1bc470160e23b788a18eb7");
        user.setName("张三");
        userTemplate.replace(user);
        System.out.println("替换后的效果");
    }

    @PostConstruct
    public void update() {
        String name = "张三";
        int age = 1;
        int num = 2;
        userTemplate.update(name, age, num);
    }
}