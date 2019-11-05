package com.likaladi.goods.service;

import com.likaladi.base.BaseService;
import com.likaladi.base.PageResult;
import com.likaladi.goods.dto.SpecQueryDto;
import com.likaladi.goods.entity.Specification;
import com.likaladi.goods.entity.SpuDetail;
import com.likaladi.goods.vo.SpecParamVo;
import com.likaladi.goods.vo.SpuSpecVo;

import java.util.List;

/**
 * @author likaladi
 * 处理规格属性相关逻辑
 */
public interface SpecService extends BaseService<Specification> {

    /**
     * 根据id查询规格属性
     * @param id
     * @return
     */
    SpecParamVo queryById(Long id);

    /**
     * 根据id列表查询对应的规格属性
     * @param ids
     * @return
     */
    SpuSpecVo getSpuSpecVo(List<Long> ids);

    /**
     * 分页查询规格属性
     * @param specQueryDto
     * @return
     */
    PageResult<SpecParamVo> listByPage(SpecQueryDto specQueryDto);

    /**
     * 根据三级分类加载规格属性
     * @param categoryId
     * @return
     */
    SpuSpecVo listByCategoryId(Long categoryId, SpuDetail spuDetail);

}
