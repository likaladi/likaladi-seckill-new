package com.likaladi.user.mapper;

import com.likaladi.base.CommonMapper;
import com.likaladi.user.entity.Role;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

public interface RoleMapper extends CommonMapper<Role> {

    @Select({
            "<script>"
              + "select * from sys_role"
              + " where 1 = 1"
              +    "<if test='params.name != null and params.name != \"\"'>"
              +      "<bind name='_name' value=\"'%' + params.name + '%'\"/>"
              +      "and name like #{_name}"
              +   "</if>"
          + "</script>"
    })
    List<Role> selectByPage(@Param("params") Map<String, Object> params);
}