package com.likaladi.user.service.impl;

import com.likaladi.base.BaseServiceImpl;
import com.likaladi.error.ErrorBuilder;
import com.likaladi.user.dto.RolePermissionDto;
import com.likaladi.user.entity.Role;
import com.likaladi.user.entity.RolePermission;
import com.likaladi.user.mapper.RoleMapper;
import com.likaladi.user.mapper.RolePermissionMapper;
import com.likaladi.user.service.RolePermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;


@Service
public class RolerPermissionServiceImpl extends BaseServiceImpl<RolePermission> implements RolePermissionService {

    @Autowired
    private RolePermissionMapper rolePermissionMapper;

    @Autowired
    private RoleMapper roleMapper;

    @Override
    public void setPermissionToRole(RolePermissionDto rolePermissionDto) {
        Role role = roleMapper.selectByPrimaryKey(rolePermissionDto.getRoleId());
        if(Objects.isNull(role)){
            ErrorBuilder.throwMsg("角色不存在");
        }

        RolePermission rolePermission = new RolePermission();
        rolePermission.setRoleId(rolePermissionDto.getRoleId());
        int result = rolePermissionMapper.delete(rolePermission);
        if(result <= 0){
            ErrorBuilder.throwMsg("删除角色权限失败");
        }

        List<RolePermission> rolePermissions = rolePermissionDto.getPermissionIds().stream().map(permissionId -> {
            return RolePermission.builder()
                    .roleId(rolePermissionDto.getRoleId())
                    .permissionId(permissionId)
                    .build();
        }).collect(Collectors.toList());

        this.save(rolePermissions);
    }
}
