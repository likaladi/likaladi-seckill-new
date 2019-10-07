package com.likaladi.user.entity;

import javax.persistence.*;

import com.likaladi.base.BaseEntity;
import lombok.Data;

@Data
@Table(name = "sys_permission")
public class Permission extends BaseEntity {

    /**
     * 权限标识
     */
    private String permission;

    /**
     * 名称
     */
    private String name;
}