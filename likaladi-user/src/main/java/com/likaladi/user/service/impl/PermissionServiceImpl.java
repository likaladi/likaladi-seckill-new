package com.likaladi.user.service.impl;

import com.likaladi.base.BaseServiceImpl;
import com.likaladi.user.entity.Permission;
import com.likaladi.user.mapper.PermissionMapper;
import com.likaladi.user.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PermissionServiceImpl extends BaseServiceImpl<Permission> implements PermissionService {

    @Autowired
    private PermissionMapper permissionMapper;

    @Override
    public List<Permission> getPermission(Long roleId) {
        return permissionMapper.getPermission(roleId);
    }
}
