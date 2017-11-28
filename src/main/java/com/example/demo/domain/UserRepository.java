package com.example.demo.domain;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * descript
 *
 * @author orange
 * @Time 2017/11/27 0027
 */
@Repository
public interface UserRepository extends MongoRepository<User, String> {

    /**
     * JPA 查询
     * @param name
     * @return
     */
    List<User> findByName(String name);

    /**
     * 分页 排序
     * @param age
     * @param pageable
     * @return
     */
    Page<User> findByAge(int age, Pageable pageable);

    /**
     * 排序
     * @param age
     * @param sort
     * @return
     */
    List<User> findByAge(int age, Sort sort);

    /**
     * 根据条件去重
     * @return
     */
    List<User> findDistinctByName();

    /**
     * 忽略大小写
     * @param age
     * @param pageable
     * @return
     */
    Page<User> findByAgeIgnoreCase(int age , Pageable pageable);

    /**
     * 原生语句
     * @return
     */
    @Query(value = "{$or:[{'age':112},{'username':'张三'}]}")
    List<User> findByQuery();

}
