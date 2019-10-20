package com.likaladi.base;

import java.util.List;

public interface BaseService<T> {

    /**
     * 存储对象
     * @param t
     */
    void save(T t);

    /**
     * 批量持久化对象列表
     * @param models
     */
    void save(List<T> models);

    /**
     * 根据主键id删除对象
     * @param id
     */
    void deleteById(Object id);


    /**
     * 根据ids集合删除对象
     * @param ids
     */
    void deleteByIdList(List ids);

    void deleteByCondition(T t);

    /**
     * 根据id查询对象
     * @param id
     * @return
     */
    T findById(Object id);

    /**
     * 通过Model中某个成员变量名称（非数据表中column的名称）查找,value需符合unique约束
     * @param fieldName 对象成员变量名称
     * @param value
     * @return
     */
    T findBy(String fieldName, Object value);

    List<T> findListBy(String fieldName, Object value);

    List<T> findListBy(List<String> fieldNames, List<Object> values);

    /**
     * 根据多个ids查询对象列表
     * @param ids
     * @return
     */
    List<T> findByIds(List ids);

    /**
     * 获取所有对象
     * @return
     */
    List<T> findAll();//获取所有


    /**
     * 更新对象信息
     * @param t
     */
    void update(T t);


}
