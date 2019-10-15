package com.likaladi.user.entity;

import javax.persistence.*;

import com.likaladi.base.BaseEntity;
import lombok.Data;

@Data
@Table(name = "sys_role")
public class Role extends BaseEntity {

    /**
     * 角色code
     */
    private String code;

    /**
     * 角色名
     */
    private String name;
}