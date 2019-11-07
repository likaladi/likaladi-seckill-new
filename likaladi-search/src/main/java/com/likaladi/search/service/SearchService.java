package com.likaladi.search.service;

import com.likaladi.base.PageResult;
import com.likaladi.goods.vo.SpuDetailVo;
import com.likaladi.goods.vo.SpuVo;
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

    /**
     * 导入数据
     * @param spuVo
     * @param spuDetailVo
     * @return
     */
    Goods buildGoods(SpuVo spuVo, SpuDetailVo spuDetailVo);

    PageResult<Goods> search(SearchRequest request);
}
