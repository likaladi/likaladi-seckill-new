package com.likaladi.goods.entity;

import com.likaladi.base.BaseEntity;

import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.*;
import lombok.Data;

@Data
@Table(name = "spu")
public class Spu extends BaseEntity {

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
     * 最低销售价
     */
    @Column(name = "min_price")
    private BigDecimal minPrice;

    /**
     * 最高销售价
     */
    @Column(name = "max_price")
    private BigDecimal maxPrice;

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

}
