package com.example.demo.infrastructure.persistence;

import com.example.demo.domain.School;
import com.example.demo.domain.User;
import com.example.demo.domain.UserTemplate;
import com.example.demo.domain.pojo.HostingCount;
import com.example.demo.util.GsonUtil;
import com.example.demo.util.Paginate;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.WriteResult;
import com.mongodb.util.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.mapreduce.GroupBy;
import org.springframework.data.mongodb.core.mapreduce.GroupByResults;
import org.springframework.data.mongodb.core.query.BasicQuery;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import java.util.Iterator;
import java.util.List;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.*;

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
        BasicQuery query2 = new BasicQuery("{ age : { $gt : 1 }, name : '" + name + "' }");
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
        query.addCriteria(Criteria.where("name").regex(name, "i"));
        // 法二
//        query.addCriteria(Criteria.where("name").regex(
//                Pattern.compile(Pattern.quote(name), Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CASE)));
        return mongoTemplate.find(query, User.class);
    }

    @Override
    public List<User> findAll(String name, Paginate paginate) {
        Criteria criteria = Criteria.where("name").is(name);

        paginate.setTotalNumber(mongoTemplate.count(new Query(criteria), User.class));
        paginate.calcTotalPageNumber();

        Query query = new Query(criteria).with(new Sort(Sort.Direction.DESC, "age"));
        query.skip(paginate.caclStartNum()).limit(paginate.getPage());

        return mongoTemplate.find(query, User.class);
    }

    @Override
    public List<User> findWithPageable(String name, Pageable pageable) {
        Criteria criteria = Criteria.where("name").is(name);

        Query query = new Query(criteria).with(pageable);

        return mongoTemplate.find(query, User.class);
    }

    @Override
    public List<User> findWithSort(String name, Sort sort) {
        Criteria criteria = Criteria.where("name").is(name);

        Query query = new Query(criteria).with(sort);

        return mongoTemplate.find(query, User.class);
    }

    @Override
    public long count(String name) {
        Criteria criteria = Criteria.where("name").is(name);

        Query query = new Query(criteria);

        return mongoTemplate.count(query, User.class);
    }

    @Override
    public long delete(String id) {
        Criteria criteria = Criteria.where("_id").is(id);

        WriteResult writeResult = mongoTemplate.remove(new Query(criteria), User.class);

        return writeResult.getN();
    }

    @Override
    public Integer deleteProperty(String name) {
        Criteria criteria = Criteria.where("name").is(name);

        Update update = new Update().unset("age");

        Query query = new Query(criteria);

        WriteResult writeResult = mongoTemplate.updateFirst(query, update, User.class);

        return writeResult.getN();
    }

    @Override
    public List<HostingCount> aggregateQuery(String distinct, int age) {
        Criteria criteria = Criteria.where("age").gt(age);
        Aggregation agg = newAggregation(
                match(criteria),
                group("name", "age").count().as("total"),
                project("total", "age", "name").and("content").previousOperation(),
                sort(Sort.Direction.DESC, "total")
        );

        //Convert the aggregation result into a List
        AggregationResults<HostingCount> groupResults
                = mongoTemplate.aggregate(agg, User.class, HostingCount.class);

        return groupResults.getMappedResults();

    }


    @Override
    public Iterator<HostingCount> groupQuery(int age) {
        Criteria criteria = Criteria.where("age").gt(age);

        // 这是一个demo  实际的情况要实际而定
        GroupBy groupBy = GroupBy.key("teacherId").initialDocument("{teacherId: 0, " +
                "workTopicCount: 0, submitWorkCount: 0, " +
                "correctWorkCount: 0, wrongWorkCount: 0, " +
                "correctEmendCount: 0, groupWorkCount: 0, workCount: 0} ")

                .reduceFunction("function(key, result){"
                        + "result.teacherId = key.teacherId;"
                        + "result.workTopicCount += key.topicNum;"
                        + "result.submitWorkCount += key.handIn; "
                        + "result.correctWorkCount += key.correctNum;"
                        + "if(key.workFlag == 2) {result.wrongWorkCount += 1}; "
                        + "if(key.correctEmendNum > 0){result.correctEmendCount += 1}; "
                        + "key.targets.forEach(function(value, index, array) {" +
                        "if(value.groupFlag ==2){" +
                        "result.groupWorkCount += 1;" +
                        "return;}});"
                        + "result.workCount += 1;}");

        GroupByResults<HostingCount> group=mongoTemplate.group(criteria,
                mongoTemplate.getCollectionName(User.class),
                groupBy, HostingCount.class);
        return group.iterator();
    }

    @Override
    public Iterator<User> distinctQuery(String name) {
        Criteria criteria = Criteria.where("name").is(name);

        Query query = new Query(criteria);

        // {"age":22,"locate":" 北京","name":"dolphin"}
        GroupBy groupBy = GroupBy.key("name");
        DBObject initialDocument = new BasicDBObject();
        initialDocument.put("age", 0);
        groupBy.initialDocument(initialDocument);
        String reduceFunction= "function(cur, result){" +
                "if(cur.age>result.age)" +
                "result.age = cur.age;" +
                "result.name = cur.name;result._id = cur._id}";
        groupBy.reduceFunction(reduceFunction);
        GroupByResults<User>  results = mongoTemplate.group(mongoTemplate.getCollectionName(User.class), groupBy, User.class);
        return results.iterator();
    }

    @Override
    public Boolean exist(String name) {
        Criteria criteria = Criteria.where("name").is(name);

        Query query = new Query(criteria);
        return mongoTemplate.exists(query,User.class);
    }
}
