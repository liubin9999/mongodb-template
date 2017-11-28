package com.example.demo.service;

import com.example.demo.domain.School;
import com.example.demo.domain.User;
import com.example.demo.domain.UserTemplate;
import com.example.demo.util.Paginate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

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


    private void save() {
        User user = new User();
        user.createUserCase();
        userTemplate.save(user);
    }

    private void repalce() {
        User user = userTemplate.findById("5a1bc470160e23b788a18eb7");
        user.setName("张三");
        userTemplate.replace(user);
        System.out.println("替换后的效果");
    }


    private void update() {
        String name = "张三";
        int age = 1;
        int num = 2;
        userTemplate.update(name, age, num);
    }

    private void addToSet() {
        String name = "张三";

        List<School> schoolList = new ArrayList<>();
        schoolList.add(new School(111, "学校名称5", 2015));
        schoolList.add(new School(1123, "学校名称6", 2016));

        userTemplate.addToList(name, schoolList);
    }

    private void findByNativeQuery() {
        String name = "张三";
        List<User> userList = userTemplate.nativeQuery(name);

        System.out.println("查询结果：----" + userList.toString());
    }


    private void findAll() {
        int age = 0;
        List<User> userList = userTemplate.findAll(age);

        System.out.println("查询结果：----" + userList.toString());
    }

    private void likeQuery(){
        String name = "齐";

        List<User> userList = userTemplate.likeQuery(name);

        System.out.println("查询结果：----" + userList.toString());
    }

    private void ignoreCaseQuery(){
        String name = "HelloWorD";

        List<User> userList = userTemplate.ignoreCaseQuery(name);

        System.out.println("查询结果：----" + userList.toString());
    }

    @PostConstruct
    private void findAllByPaginate(){
        String name = "张三";
        Paginate paginate = new Paginate();
        paginate.setPage(0);
        paginate.setSize(2);

        List<User> userList = userTemplate.findAll(name,paginate);
        System.out.println("查询结果：----" + userList.toString());
        System.out.println("\n\n查询结果：----" + paginate.toString());
    }
}
