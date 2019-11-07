package com.likaladi.goods.service.impl;

import com.likaladi.base.BaseServiceImpl;
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
}
