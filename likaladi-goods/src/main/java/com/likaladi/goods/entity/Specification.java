package com.likaladi.goods.entity;

import com.likaladi.base.BaseEntity;
import java.util.Date;
import javax.persistence.*;
import lombok.Data;

@Data
@Table(name = "specification")
public class Specification extends BaseEntity {
    /**
     * 分组名称
     */
    private String group;

    /**
     * 属性名称
     */
    private String name;

    /**
     * 属性值
     */
    private String options;

    /**
     * 操作类型：1文本框；2单选框；3复选框；4下拉框
     */
    private Integer type;

    /**
     * 属性单位
     */
    private String unit;

    /**
     * 是否全局属性：0不是；1是
     */
    @Column(name = "is_gloab")
    private Boolean isGloab;

    /**
     * 是否搜索属性：0不是；1是
     */
    @Column(name = "is_search")
    private Boolean isSearch;

    /**
     * 分类id
     */
    @Column(name = "category_id")
    private Long categoryId;
}
