package com.example.demo.domain;

/**
 * 利用mongoTemplate
 *
 * @author orange
 * @Time 2017/11/27 0027
 */

public interface UserTemplate {

    /**
     * 整体替换
     *
     * @param user
     * @return
     */
    Boolean replace(User user);

    /**
     * 根据ID查询
     * @param id
     * @return
     */
    User findById(String id);
}
