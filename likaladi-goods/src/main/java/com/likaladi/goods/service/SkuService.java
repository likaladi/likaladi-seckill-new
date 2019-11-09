package com.likaladi.goods.service;

import com.likaladi.base.BaseService;
import com.likaladi.goods.entity.Sku;
import com.likaladi.goods.entity.Spu;

import java.util.List;

/**
 * @author likaladi
 * 处理商品SKU相关逻辑
 */
public interface SkuService extends BaseService<Sku> {

    /**
     * 根据多个spuIds查询sku列表
     * @param spuIds
     * @return
     */
    List<Sku> queryBySpuIds(List<Long> spuIds);

    /**
     * 根据spuid列表更新对应的sku上下架状态
     * @param spuIds
     * @param saleable
     */
    void updateSkuStateBySpuIds(List<Long> spuIds, Integer saleable);

    /**
     * 根据skuid列表更新sku上下架状态
     * @param ids
     * @param saleable
     */
    void updateSkuStateByIds(List<Long> ids, Integer saleable);
}
