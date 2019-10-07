package com.likaladi.user.entity;

import javax.persistence.*;

import com.likaladi.base.BaseEntity;
import lombok.Data;

@Data
@Table(name = "sys_role_user")
public class RoleUser extends BaseEntity {
    /**
     * 用户id
     */
    @Column(name = "user_id")
    private Long userId;

    /**
     * 角色id
     */
    @Column(name = "role_id")
    private Long roleId;
}