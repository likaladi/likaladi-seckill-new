package com.likaladi.goods.entity;

import com.likaladi.base.BaseEntity;
import java.util.Date;
import javax.persistence.*;
import lombok.Data;

@Data
@Table(name = "spu")
public class Spu extends BaseEntity {
    /**
     * spu id
     */
    @GeneratedValue(generator = "JDBC")
    private Long id;

    /**
     * 商家id
     */
    @Column(name = "seller_id")
    private Long sellerId;

    /**
     * 标题
     */
    private String title;

    /**
     * 子标题
     */
    @Column(name = "sub_title")
    private String subTitle;

    /**
     * 主图
     */
    @Column(name = "image")
    private String image;

    /**
     * 1级类目id
     */
    private Long cid1;

    /**
     * 2级类目id
     */
    private Long cid2;

    /**
     * 3级类目id
     */
    private Long cid3;

    /**
     * 商品所属品牌id
     */
    @Column(name = "brand_id")
    private Long brandId;

    /**
     * 是否上架，0下架，1上架
     */
    private Boolean saleable;

    /**
     * 是否有效，0已删除，1有效
     */
    @Column(name = "is_enable")
    private Boolean isEnable;

    /**
     * 添加时间
     */
    @Column(name = "create_time")
    private Date createTime;

    /**
     * 更新时间
     */
    @Column(name = "update_time")
    private Date updateTime;
}
