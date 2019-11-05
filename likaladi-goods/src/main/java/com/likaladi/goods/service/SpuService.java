package com.likaladi.goods.service;

import com.likaladi.base.BaseService;
import com.likaladi.base.PageResult;
import com.likaladi.goods.dto.SpuDto;
import com.likaladi.goods.dto.SpuQueryDto;
import com.likaladi.goods.entity.Specification;
import com.likaladi.goods.entity.Spu;
import com.likaladi.goods.vo.SpuDetailVo;
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
     * 编辑商品
     * @param spuDto
     */
    void updateSpuSku(SpuDto spuDto);

    /**
     * 删除商品
     * @param ids
     */
    void deleteSpuSKu(List<Long> ids);

    /**
     * 根据商品id查询商品信息
     * @param id
     * @return
     */
    SpuVo querySpuById(Long id);

    /**
     * 根据商品id查询商品详情SpuDetail
     * @param id
     * @return
     */
    SpuDetailVo querySpuSkuDetail(Long id);

    /**
     * 分页查询商品
     * @param spuQueryDto
     * @return
     */
    PageResult<SpuVo> listByPage(SpuQueryDto spuQueryDto);

    /**
     * 查询规格对应的分类是否存在记录
     * @param categoryIds
     * @return
     */
    int queryCountByCateogryIds(List<Long> categoryIds);
}
