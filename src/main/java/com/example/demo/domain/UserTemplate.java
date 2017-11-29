package com.example.demo.domain;

import com.example.demo.domain.pojo.HostingCount;
import com.example.demo.util.Paginate;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.Iterator;
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

    /**
     * 模糊查询
     *
     * @param name
     * @return
     */
    List<User> likeQuery(String name);

    /**
     * 忽略大小写
     *
     * @param name
     * @return
     */
    List<User> ignoreCaseQuery(String name);

    /**
     * 分页获取 -- 自定义
     *
     * @param name
     * @param paginate
     * @return
     */
    List<User> findAll(String name, Paginate paginate);

    /**
     * 分页、排序查询 --推荐
     *
     * @param name
     * @param pageable
     * @return
     */
    List<User> findWithPageable(String name, Pageable pageable);

    /**
     * 排序查询、不分页 --推荐
     *
     * @param name
     * @param sort
     * @return
     */
    List<User> findWithSort(String name, Sort sort);

    /**
     * 计算总数
     *
     * @param name
     * @return
     */
    long count(String name);

    /**
     * 删除
     *
     * @param id
     * @return
     */
    long delete(String id);

    /**
     * 删除指定属性
     *
     * @param name
     * @return
     */
    Integer deleteProperty(String name);

    /**
     * 聚合查询
     * <pre>https://docs.mongodb.com/manual/aggregation/</pre>
     *
     * @param distinct
     * @param age
     * @return
     */
    List<HostingCount> aggregateQuery(String distinct, int age);

    /**
     * 聚合查询
     *
     * @param age
     * @return
     */
    Iterator<HostingCount> groupQuery(int age);

    /**
     * 去重查询
     *
     * @return
     */
    Iterator<User> distinctQuery(String name);

    /**
     * 判断数据是否存在
     *
     * @param name
     * @return
     */
    Boolean exist(String name);


    // 删除集合中指定元素
}
