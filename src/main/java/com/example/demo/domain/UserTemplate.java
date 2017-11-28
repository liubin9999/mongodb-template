package com.example.demo.domain;

import java.util.List;

/**
 * 利用mongoTemplate
 *
 * @author orange
 * @Time 2017/11/27 0027
 */

public interface UserTemplate {


    /**
     * 保存
     *
     * @param user
     */
    String save(User user);

    /**
     * 批量查询
     *
     * @param age
     * @return
     */
    List<User> findAll(int age);

    /**
     * 原生查询
     *
     * @param name
     * @return
     */
    List<User> nativeQuery(String name);

    /**
     * 整体替换
     *
     * @param user
     * @return
     */
    Boolean replace(User user);

    /**
     * 根据ID查询
     *
     * @param id
     * @return
     */
    User findById(String id);

    /**
     * 更新
     *
     * @param name
     * @param age
     * @param num
     */
    void update(String name, int age, int num);

    /**
     * 集合中插入元素
     *
     * @param name
     * @param schools
     */
    void addToList(String name, List<School> schools);

}
