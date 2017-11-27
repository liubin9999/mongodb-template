package com.example.demo.infrastructure.persistence;

import com.example.demo.domain.User;
import com.example.demo.domain.UserRepository;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * 接口实现
 *
 * @author orange
 * @Time 2017/11/27 0027
 */
@Repository
public interface UserRepositoryJPA extends MongoRepository<User, String>, UserRepository {
}
