package com.example.demo.domain;

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
}
