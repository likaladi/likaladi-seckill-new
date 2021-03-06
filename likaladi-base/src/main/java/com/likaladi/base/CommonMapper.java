package com.likaladi.base;


import tk.mybatis.mapper.additional.idlist.DeleteByIdListMapper;
import tk.mybatis.mapper.additional.idlist.SelectByIdListMapper;
import tk.mybatis.mapper.common.BaseMapper;
import tk.mybatis.mapper.common.ConditionMapper;
import tk.mybatis.mapper.common.IdsMapper;
import tk.mybatis.mapper.common.special.InsertListMapper;

/**
 * @author likaladi
 * MyMapper接口不能被扫描，所以不能跟具体的mapper放在同一目录
 * @param <T>
 */
public interface CommonMapper<T> extends BaseMapper<T>, ConditionMapper<T>, IdsMapper<T>,
        InsertListMapper<T>, DeleteByIdListMapper, SelectByIdListMapper {
}
