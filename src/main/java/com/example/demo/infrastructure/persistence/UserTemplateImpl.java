package com.example.demo.infrastructure.persistence;

import com.example.demo.domain.User;
import com.example.demo.domain.UserTemplate;
import com.example.demo.util.GsonUtil;
import com.mongodb.DBObject;
import com.mongodb.WriteResult;
import com.mongodb.util.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

/**
 * 非JPA接口实现
 *
 * @author orange
 * @Time 2017/11/27 0027
 */
@Repository
public class UserTemplateImpl implements UserTemplate {

    @Autowired
    private MongoTemplate mongoTemplate;


    @Override
    public String save(User user) {

        mongoTemplate.insert(user);

        return user.getId();
    }

    @Override
    public Boolean replace(User user) {
        Criteria criteria = Criteria.where("_id").is(user.getId())
                .and("age").is(user.getAge());
        Update update = Update.fromDBObject((DBObject) JSON.parse(GsonUtil.gson().toJson(user)));

        WriteResult wr = mongoTemplate.updateFirst(Query.query(criteria), update, User.class);

        if (wr.getN() > 0) {
            return true;
        }
        return false;
    }

    @Override
    public User findById(String id) {

        return mongoTemplate.findById(id, User.class);
    }

    @Override
    public void update(String name, int age, int num) {
        Criteria criteria = Criteria.where("name").is(name).orOperator(Criteria.where("age").is(age));

        Query query = new Query(criteria);

        Update update = new Update().inc("age",num);
//        Update update = new Update().set("age",1);

        mongoTemplate.updateMulti(query, update, User.class);
    }
}
