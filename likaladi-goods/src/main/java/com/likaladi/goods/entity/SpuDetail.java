package com.likaladi.goods.entity;

import javax.persistence.*;
import lombok.Data;
import java.io.Serializable;

@Data
@Table(name = "spu_detail")
public class SpuDetail implements Serializable {

    @Id
    @Column(name = "spu_id")
    private Long spuId;

    /**
     * 全部属性数据
     */
    private String specifications;

    /**
     * 特有规格参数及可选值信息，json格式
     */
    @Column(name = "spec_template")
    private String specTemplate;

    /**
     * 包装清单
     */
    @Column(name = "packing_list")
    private String packingList;

    /**
     * 售后服务
     */
    @Column(name = "after_service")
    private String afterService;

    /**
     * 商品描述信息
     */
    private String description;
}
