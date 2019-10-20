package com.likaladi.goods.entity;

import com.likaladi.base.BaseEntity;
import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "category_brand")
public class CategoryBrand implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 分类id
     */
    @Column(name = "category_id")
    private Long categoryId;

    /**
     * 品牌id
     */
    @Column(name = "brand_id")
    private Long brandId;
}
