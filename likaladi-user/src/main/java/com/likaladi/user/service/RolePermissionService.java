package com.likaladi.user.service;

import com.likaladi.base.BaseService;
import com.likaladi.user.dto.RolePermissionDto;
import com.likaladi.user.entity.RolePermission;

/**
 * @author liwen
 * 处理角色授权相关逻辑
 */
public interface RolePermissionService extends BaseService<RolePermission> {

    /**
     * 角色分配权限
     * @param rolePermissionDto
     */
    void setPermissionToRole(RolePermissionDto rolePermissionDto);
}
