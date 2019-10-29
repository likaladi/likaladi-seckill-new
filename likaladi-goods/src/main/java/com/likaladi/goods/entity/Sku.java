package com.likaladi.goods.entity;

import com.likaladi.base.BaseEntity;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.*;
import lombok.Data;

@Data
@Table(name = "sku")
public class Sku extends BaseEntity {

    /**
     * spu id
     */
    @Column(name = "spu_id")
    private Long spuId;

    /**
     * 商品的图片，json数字存储
     */
    private String images;

    /**
     * 商品条形码
     */
    private String barcode;

    /**
     * 销售价格
     */
    private BigDecimal price;

    /**
     * 特有规格属性在spu属性模板中的对应下标组合
     */
    private String indexes;

    /**
     * sku的特有规格参数键值对，json格式，反序列化时请使用linkedHashMap，保证有序
     */
    @Column(name = "own_spec")
    private String ownSpec;

    /**
     * 库存数量
     */
    @Column(name = "stock_num")
    private Integer stockNum;

    /**
     * 是否有效，0已删除，1有效
     */
    @Column(name = "is_enable")
    private Boolean isEnable;
}
