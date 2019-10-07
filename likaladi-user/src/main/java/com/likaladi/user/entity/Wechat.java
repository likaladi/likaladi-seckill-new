package com.likaladi.user.entity;

import javax.persistence.*;

import com.likaladi.base.BaseEntity;
import lombok.Data;

@Data
@Table(name = "t_wechat")
public class Wechat extends BaseEntity {

    /**
     * 微信openid
     */
    @Column(name = "open_id")
    private String openId;

    /**
     * 微信unionid
     */
    @Column(name = "union_id")
    private String unionId;

    /**
     * 绑定用户的id
     */
    @Column(name = "user_id")
    private Long userId;

    /**
     * 公众号标识
     */
    private String app;

    /**
     * 微信昵称
     */
    private String nickname;

    /**
     * 微信返回的性别
     */
    private String sex;

    /**
     * 微信返回的省
     */
    private String province;

    /**
     * 微信返回的城市
     */
    private String city;

    /**
     * 微信返回的国家
     */
    private String country;

    /**
     * 微信头像
     */
    @Column(name = "head_img_url")
    private String headImgUrl;
}