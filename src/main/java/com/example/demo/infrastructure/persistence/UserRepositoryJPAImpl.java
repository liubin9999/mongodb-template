package com.example.demo.infrastructure.persistence;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;

/**
 * 非JPA接口实现
 *
 * @author orange
 * @Time 2017/11/27 0027
 */
public class UserRepositoryJPAImpl {

    @Autowired
    private MongoTemplate mongoTemplate;
}
