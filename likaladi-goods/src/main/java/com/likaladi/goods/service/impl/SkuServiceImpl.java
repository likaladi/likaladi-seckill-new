package com.likaladi.goods.service.impl;

import com.likaladi.base.BaseServiceImpl;
import com.likaladi.error.ErrorBuilder;
import com.likaladi.goods.entity.Sku;
import com.likaladi.goods.entity.Spu;
import com.likaladi.goods.mapper.SkuMapper;
import com.likaladi.goods.service.SkuService;
import com.likaladi.goods.service.SpuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author liwen
 */
@Service
public class SkuServiceImpl extends BaseServiceImpl<Sku> implements SkuService {

    @Autowired
    private SkuMapper skuMapper;

    @Override
    public List<Sku> queryBySpuIds(List<Long> spuIds) {
        return skuMapper.queryBySpuIds(spuIds);
    }

    @Override
    public void updateSkuStateBySpuIds(List<Long> spuIds, Integer saleable) {
        int result = skuMapper.updateSkuStateBySpuIds(spuIds, saleable);
        if(result <= 0){
            ErrorBuilder.throwMsg("根据spuid更新sku上下架状态失败");
        }
    }

    @Override
    public void updateSkuStateByIds(List<Long> ids, Integer saleable) {
        int result = skuMapper.updateSkuStateByIds(ids, saleable);
        if(result <= 0){
            ErrorBuilder.throwMsg("根据skuid列表更新sku上下架失败");
        }
    }
}
