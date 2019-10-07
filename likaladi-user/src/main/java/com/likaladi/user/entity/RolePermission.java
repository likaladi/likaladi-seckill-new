package com.likaladi.user.entity;

import javax.persistence.*;

import com.likaladi.base.BaseEntity;
import lombok.Data;

@Data
@Table(name = "sys_role_permission")
public class RolePermission extends BaseEntity {

    /**
     * 角色id
     */
    @Column(name = "role_id")
    private Long roleId;

    /**
     * 权限id
     */
    @Column(name = "permission_id")
    private Long permissionId;
}