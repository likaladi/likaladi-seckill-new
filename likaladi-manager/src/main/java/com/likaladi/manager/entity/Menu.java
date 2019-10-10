package com.likaladi.manager.entity;

import com.likaladi.base.BaseEntity;
import java.util.Date;
import java.util.List;
import javax.persistence.*;
import lombok.Data;

@Data
@Table(name = "menu")
public class Menu extends BaseEntity {

    /**
     * 父菜单id
     */
    @Column(name = "parent_id")
    private Long parentId;

    /**
     * 菜单名
     */
    private String name;

    /**
     * 菜单url
     */
    private String url;

    /**
     * 菜单样式
     */
    private String css;

    /**
     * 排序
     */
    private Integer sort;

    /**
     * 子菜单列表
     */
    private List<Menu> child;

}