package com.likaladi.user.service;

import com.likaladi.base.BaseService;
import com.likaladi.common.PageResult;
import com.likaladi.user.entity.Role;

import java.util.Map;

/**
 * @author liwen
 * 处理角色相关逻辑
 */
public interface RoleService extends BaseService<Role> {

    /**
     * 分页查询角色列表
     * @param params 查询条件
     * @return
     */
    PageResult<Role> queryRolesByPage(Map<String, Object> params);
}
