package com.example.demo.domain;

import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * descript
 *
 * @author orange
 * @Time 2017/11/27 0027
 */
public interface UserRepository extends MongoRepository<User, String> {

    /**
     * JPA 查询
     * @param name
     * @return
     */
    User findByName(String name);
}
