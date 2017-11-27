package com.example.demo.service;

import com.example.demo.domain.BodyBuild;
import com.example.demo.domain.School;
import com.example.demo.domain.User;
import com.example.demo.domain.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

/**
 * 应用层
 *
 * @author orange
 * @Time 2017/11/27 0027
 */
@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @PostConstruct
    public void save(){
        User user = new User();
        user.setName("张三");
        user.setAge(112);

        List<String> address = new ArrayList<>();
        address.add("杭州");
        address.add("上海");
        user.setAddress(address);

        user.setBodyBuild(new BodyBuild(111,222, "A"));

        List<School> schoolList = new ArrayList<>();
        schoolList.add(new School(111,"学校名称",2015));
        schoolList.add(new School(1123,"学校名称--2",2016));
        user.setSchools(schoolList);

        userRepository.save(user);
    }
}
