package com.likaladi.goods.entity;

import com.likaladi.base.BaseEntity;
import javax.persistence.*;
import lombok.Data;

import java.io.Serializable;

@Data
@Table(name = "category")
public class Category implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 类目名称
     */
    private String name;

    /**
     * 父类目id,顶级类目填0
     */
    @Column(name = "parent_id")
    private Long parentId;

    /**
     * 是否为父节点，0为否，1为是
     */
    @Column(name = "is_parent")
    private Boolean isParent;

    /**
     * 排序指数，越小越靠前
     */
    private Integer sort;
}
