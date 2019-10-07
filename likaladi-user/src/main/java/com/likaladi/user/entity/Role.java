package com.likaladi.user.entity;

import javax.persistence.*;

import com.likaladi.base.BaseEntity;
import lombok.Data;

@Data
@Table(name = "sys_role")
public class Role extends BaseEntity {

    @Column(name = "role_name")
    private String roleName;

    @Column(name = "role_desc")
    private String roleDesc;
}