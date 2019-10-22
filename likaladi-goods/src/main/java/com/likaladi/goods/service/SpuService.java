package com.likaladi.goods.service;

import com.likaladi.base.BaseService;
import com.likaladi.goods.dto.SpuDto;
import com.likaladi.goods.entity.Specification;
import com.likaladi.goods.entity.Spu;
import com.likaladi.goods.vo.SpuVo;

import java.util.List;

/**
 * @author likaladi
 * 处理商品SPU相关逻辑
 */
public interface SpuService extends BaseService<Spu> {

    /**
     * 添加商品
     * @param spuDto
     */
    void saveSpuSku(SpuDto spuDto);

    /**
     * 删除商品
     * @param ids
     */
    void deleteSpuSKu(List<Long> ids);

    /**
     * 根据id查询商品
     * @param id
     * @return
     */
    SpuVo querySpuSkuById(Long id);
}
