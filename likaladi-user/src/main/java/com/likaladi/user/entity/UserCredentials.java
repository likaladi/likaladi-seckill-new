package com.likaladi.user.entity;

import javax.persistence.*;

import com.likaladi.base.BaseEntity;
import lombok.Data;

@Data
@Table(name = "user_credentials")
public class UserCredentials extends BaseEntity {
    /**
     * 用户id
     */
    @Column(name = "user_id")
    private Long userId;

    /**
     * 用户名或手机号等
     */
    private String username;

    /**
     * 账号类型（用户名、手机号）
     */
    private String type;

}