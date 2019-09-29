package com.example.demo.service;

import com.example.demo.domain.School;
import com.example.demo.domain.User;
import com.example.demo.domain.UserTemplate;
import com.example.demo.domain.pojo.HostingCount;
import com.example.demo.util.Paginate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
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

    public void save() {
        User user = new User();
        user.createUserCase();
    //    user.setId("5a31dcdf160e2338a05d5b17");
        userTemplate.save(user);
    }

    public void repalce() {
        User user = userTemplate.findById("5a1bc470160e23b788a18eb7");
        user.setName("张三");
        userTemplate.replace(user);
        System.out.println("替换后的效果");
    }


    public void update() {
        String name = "张三";
        int age = 1;
        int num = 2;
        userTemplate.update(name, age, num);
    }

    public void addToSet() {
        String name = "张三";

        List<School> schoolList = new ArrayList<>();
        schoolList.add(new School(111, "学校名称5", 2015));
        schoolList.add(new School(1123, "学校名称6", 2016));

        userTemplate.addToList(name, schoolList);
    }

    public void findByNativeQuery() {
        String name = "张三";
        List<User> userList = userTemplate.nativeQuery(name);

        System.out.println("查询结果：----" + userList.toString());
    }


    public void findAll() {
        int age = 0;
        List<User> userList = userTemplate.findAll(age);

        System.out.println("查询结果：----" + userList.toString());
    }

    public void likeQuery(){
        String name = "齐";

        List<User> userList = userTemplate.likeQuery(name);

        System.out.println("查询结果：----" + userList.toString());
    }

    public void ignoreCaseQuery(){
        String name = "HelloWorD";

        List<User> userList = userTemplate.ignoreCaseQuery(name);

        System.out.println("查询结果：----" + userList.toString());
    }

    public void findAllByPaginate(){
        String name = "张三";
        Paginate paginate = new Paginate();
        paginate.setPage(0);
        paginate.setSize(2);

//        List<User> userList = userTemplate.findAll(name,paginate);
        List<User> userList = userTemplate.findWithPageable(name,new PageRequest(1,1,new Sort("age")));
        System.out.println("查询结果：----" + userList.toString());
        System.out.println("\n\n查询结果：----" + paginate.toString());
    }


    public void count(){
        String name = "张三";
        long num = userTemplate.count(name);
        System.out.println("\n查询结果：----" + num);
    }

    public void delete(){
        String id = "5a1bca32160e23cf7046b7f6";

        long num = userTemplate.delete(id);

        System.out.println("\n\n查询结果：----" + num +"\n\n");
    }

    public void aggregateQuery(){
        String dictinc = "name";
        int age = 0 ;
        List<HostingCount> result = userTemplate.aggregateQuery(dictinc, age);

        // HostingCount(total=1, name=张三, age=112, content={ "name" : "张三" , "age" : 112})
        System.out.println("\n\n查询结果：----" + result.toString() +"\n\n");
    }

    public void distinctQuery(){
        String name = "张三";

        Iterator<User> userIterator = userTemplate.distinctQuery(name);

        while(userIterator.hasNext()){
            User user = userIterator.next();
            System.out.println("输出结果" + user.toString());
        }
    }

    public void exist(){
        String name = "22";

        Boolean existState = userTemplate.exist(name);

        System.out.println("\n\n判断结果：----" + existState +"\n\n");
    }

    public void deleteProperty(){
        String name = "张齐三";

        int num = userTemplate.deleteProperty(name);

        System.out.println("\n\n判断结果：----" + num +"\n\n");
    }
}
