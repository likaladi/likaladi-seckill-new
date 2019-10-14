package com.likaladi.user.service;

import com.github.pagehelper.Page;
import com.likaladi.base.BaseService;
import com.likaladi.user.dto.RoleConditionDto;
import com.likaladi.user.entity.Role;

/**
 * @author liwen
 * 处理角色相关逻辑
 */
public interface RoleService extends BaseService<Role> {

    /**
     * 分页查询角色
     * @param conditionDto
     * @return
     */
    Page<Role> queryRolesByPage(RoleConditionDto conditionDto);
}
