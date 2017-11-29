package com.example.demo.service;

import com.example.demo.domain.User;
import com.example.demo.domain.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * spring JPA
 *
 * @author orange
 * @Time 2017/11/27 0027
 */
@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public void save() {
        User user = new User();
        user.createUserCase();
        userRepository.save(user);
    }


    public void getByJPA() {
        List<User> users2 = userRepository.findByName("张三");
        System.out.println("*******" + users2.toString());

        // 查询获取 (总条数：3)
        // 情形一  ：分页 排序
        Page<User> users = userRepository.findAll(new PageRequest(0, 2, Sort.Direction.ASC, "age"));
        List<User> userList = new ArrayList<>();
        users.forEach(user1 -> {
            userList.add(user1);
        });
        int totalPages = users.getTotalPages(); //总页数 2
        int totalElements = users.getNumberOfElements(); // 当前页内的数据条数 2
        System.out.println("totalPages --->" + totalPages + "totalElements---->" + totalElements);

        //情形二  ：仅排序
        List<User> userList2 = new ArrayList<>();
        Iterable<User> userIterable = userRepository.findAll(new Sort(Sort.Direction.ASC, "age"));
        userIterable.forEach(user1 -> {
            userList2.add(user1);
        });

        //情形三 ：按条件分页排序
        Page<User> page = userRepository.findByAge(122, new PageRequest(0, 2, Sort.Direction.ASC, "age"));
        System.out.println(page.toString());
    }

    public void nativeSql() {
        List<User> userList = userRepository.findByQuery();
        System.out.println("******原生查询语句" + userList.toString());
    }


    public void distinctQuery() {
        List<User> userList = userRepository.findDistinctByName();

        System.out.println("\n\n查询结果：----" + userList.toString() + "\n\n");
    }
}
