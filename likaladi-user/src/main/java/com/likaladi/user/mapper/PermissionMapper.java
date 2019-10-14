package com.likaladi.user.mapper;

import com.likaladi.base.CommonMapper;
import com.likaladi.user.entity.Permission;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface PermissionMapper extends CommonMapper<Permission> {
    @Select("select p.* from sys_role_permission rp" +
            " LEFT JOIN sys_permission p on (rp.permission_id = p.id)" +
            " where rp.role_id = #{roleId}")
    List<Permission> getPermission(@Param("roleId") Long roleId);
}