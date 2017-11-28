package com.example.demo.infrastructure.persistence;

import com.example.demo.domain.School;
import com.example.demo.domain.User;
import com.example.demo.domain.UserTemplate;
import com.example.demo.util.GsonUtil;
import com.example.demo.util.Paginate;
import com.mongodb.DBObject;
import com.mongodb.WriteResult;
import com.mongodb.util.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.BasicQuery;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import java.util.List;

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
    public List<User> findAll(int age) {
        Criteria criteria = Criteria.where("age").gt(age);
        Query query = new Query(criteria)
                .with(new Sort(Sort.Direction.DESC, "age"));

        return mongoTemplate.find(query, User.class);
    }

    @Override
    public List<User> nativeQuery(String name) {
        BasicQuery query2 = new BasicQuery("{ age : { $gt : 1 }, name : '"+ name +"' }");
        return mongoTemplate.find(query2, User.class);
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

        Update update = new Update().inc("age", num);
//        Update update = new Update().set("age",1);

        //1、有则改变（改变多条）   无则不改变（不创建） 返回更新后的值（多条显示最新一条），无则返回null
        //mongoTemplate.findAndModify(query, update, User.class);

        //2、有则改变   无则创建一条新纪录  只更新一条记录
        // mongoTemplate.upsert(query,update,User.class);

        mongoTemplate.updateMulti(query, update, User.class);
    }

    @Override
    public void addToList(String name, List<School> schools) {
        Criteria criteria = Criteria.where("name").is(name);

        Update update = new Update();
        //  update.addToSet("schools", schools.get(0));  --每次只能添加一个
        //  update.push("schools", schools.get(0))  --每次只能添加一个;

        // 每次添加多个
        // db.applyUsers.update({"_id":"123"},{"addToSet":{"groupIDs":{"$each":["id1","id2","id3"]}}})
        Update.AddToSetBuilder ab = update.new AddToSetBuilder("schools");
        update = ab.each(schools);

        mongoTemplate.updateFirst(new Query(criteria), update, User.class);
    }

    @Override
    public List<User> likeQuery(String name) {
        Query query = new Query();
        query.addCriteria(Criteria.where("name").regex(name));

// 法二：BasicQuery query = new BasicQuery("{\"name\": {$regex : '" + name + "'} }");

        return mongoTemplate.find(query, User.class);
    }

    @Override
    public List<User> ignoreCaseQuery(String name) {
        Query query = new Query();
        // 法一
        query.addCriteria(Criteria.where("name").regex(name,"i"));
        // 法二
//        query.addCriteria(Criteria.where("name").regex(
//                Pattern.compile(Pattern.quote(name), Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CASE)));
        return mongoTemplate.find(query, User.class);
    }

    @Override
    public List<User> findAll(String name, Paginate paginate) {
        Criteria criteria = Criteria.where("name").is(name);

        paginate.setTotalNumber(mongoTemplate.count(new Query(criteria),User.class));
        paginate.calcTotalPageNumber();

        Query query = new Query(criteria).with(new Sort(Sort.Direction.DESC, "age"));
        query.skip(paginate.caclStartNum()).limit(paginate.getPage());

        return mongoTemplate.find(query,User.class);
    }
}
