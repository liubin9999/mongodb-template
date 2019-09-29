package com.example.demo.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

/**
 * 用户
 *
 * @author orange
 * @Time 2017/11/27 0027
 * <pre>根据开闭原则，set方法理论上不应该对外开放</pre>
 * <pre>方法的权限要严谨，限制</pre>
 */
@Document
@Data
@NoArgsConstructor
public class User {

    /**
     * 主键
     */
    @Id
    private String id;

    /**
     * 姓名
     */
    private String name;

    /**
     * 年纪
     */
    private int age;

    /**
     * 地址
     */
    private List<String> address;

    /**
     * 体型
     */
    private BodyBuild bodyBuild;

    /**
     * 学校
     */
    private List<School> schools;


    /**
     * 创建一个实例
     */
    public void createUserCase(){
        this.name = "张三33";
        this.age = 33;

        List<String> address = new ArrayList<>();
        address.add("杭州");
        address.add("上海");
        this.address = address;

        BodyBuild bodyBuild = new BodyBuild(3, 2, "111aaa");
        this.bodyBuild = bodyBuild;

        List<School> schoolList = new ArrayList<>();
    //    schoolList.add(new School(111, "学校名称", 2015));
    //   schoolList.add(new School(1123, "学校名称--2", 2016));
        this.schools = schoolList;
    }

}
