package com.likaladi.search.entity;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @author likaladi
 * goods对应的sku对象
 */
@Data
@Builder
public class Sku {

    /**
     * skuId
     */
    private Long id;

    /**
     * spu标题
     */
    private String title;

    /**
     * sku图片
     */
    private String image;

    /**
     * sku价格
     */
    private Double price;

}
