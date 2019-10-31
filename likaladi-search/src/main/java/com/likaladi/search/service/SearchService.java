package com.likaladi.search.service;

import com.likaladi.base.PageResult;
import com.likaladi.search.entity.Goods;
import com.likaladi.search.entity.SearchRequest;

/**
 * 创建elasticsearch商品相关逻辑
 */
public interface SearchService {

    /**
     * 创建goods索引
     * @param spuId
     */
    void createIndex(Long spuId);

    /**
     * 删除goods索引
     * @param spuId
     */
    void deleteIndex(Long spuId);

    PageResult<Goods> search(SearchRequest request);
}
