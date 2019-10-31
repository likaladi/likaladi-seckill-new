package com.likaladi.search.entity;

import lombok.Data;
import java.util.Map;

/**
 * @author likaladi
 * 商品搜索条件
 */
@Data
public class SearchRequest {

    /**
     * 搜索条件
     */
    private String key;

    /**
     * 当前页
     */
    private Integer page;

    /**
     * 每页大小
     */
    private Integer size;

    /**
     * 排序字段
     */
    private String sortBy;

    /**
     * 是否降序
     */
    private Boolean  descending;


    /**
     * 过滤条件
     */
    private Map<String, String> filter;

    /**
     * 每页大小，不从页面接收，而是固定大小
     */
    private static final Integer DEFAULT_SIZE = 20;

    /**
     * 默认页
     */
    private static final Integer DEFAULT_PAGE = 1;


    public Integer getPage() {
        if(page == null){
          return DEFAULT_PAGE;
        }
        // 获取页码时做一些校验，不能小于1
        return Math.max(DEFAULT_PAGE, page);
    }

    public Integer getSize() {

        if(size == null){
            return DEFAULT_SIZE;
        }
        return size;
    }
}
