package com.likaladi.user.service;

import com.likaladi.base.BaseService;
import com.likaladi.user.entity.Permission;
import com.likaladi.user.entity.Role;

import java.util.List;
import java.util.Set;

/**
 * @author liwen
 * 处理权限相关逻辑
 */
public interface PermissionService extends BaseService<Permission> {

    /**
     * 根据角色id获取对应权限
     * @param roleId
     * @return
     */
    List<Permission> getPermission(Long roleId);

}
