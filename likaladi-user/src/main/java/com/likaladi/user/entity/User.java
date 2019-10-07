package com.likaladi.user.entity;

import javax.persistence.*;

import com.likaladi.base.BaseEntity;
import lombok.Data;

@Data
@Table(name = "app_user")
public class User extends BaseEntity {

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 昵称
     */
    private String nickname;

    /**
     * 头像url
     */
    @Column(name = "head_img_url")
    private String headImgUrl;

    /**
     * 手机号
     */
    private String phone;

    /**
     * 性别
     */
    private Boolean sex;

    /**
     * 状态（1有效,0无效）
     */
    private Boolean enabled;

    /**
     * 类型（暂未用）
     */
    private String type;
}