package com.likaladi.search.service;

import com.likaladi.goods.vo.SpuVo;
import com.likaladi.search.entity.Goods;

/**
 * 创建elasticsearch商品相关逻辑
 */
public interface SearchService {

    /**
     * 创建elasticsearch商品Goods对象
     * @param spuVo
     * @return
     */
    Goods buildGoods(SpuVo spuVo);
}
