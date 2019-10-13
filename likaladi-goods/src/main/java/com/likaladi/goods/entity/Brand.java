package com.likaladi.goods.entity;

import javax.persistence.*;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
@Table(name = "brand")
public class Brand implements Serializable {

    @GeneratedValue(generator = "JDBC")
    private Long id;

    /**
     * 品牌名称
     */
    private String name;

    /**
     * 品牌图片地址
     */
    private String image;

    /**
     * 品牌的首字母
     */
    @Column(name = "first_char")
    private String firstChar;
}