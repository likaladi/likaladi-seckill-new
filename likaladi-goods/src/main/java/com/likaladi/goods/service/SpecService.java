package com.likaladi.goods.service;

import com.likaladi.base.BaseService;
import com.likaladi.base.PageResult;
import com.likaladi.goods.dto.SpecQueryDto;
import com.likaladi.goods.entity.Specification;
import com.likaladi.goods.vo.CategorySpecAttrVo;
import com.likaladi.goods.vo.SpecVo;

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
    SpecVo queryById(Long id);

    /**
     * 分页查询规格属性
     * @param specQueryDto
     * @return
     */
    PageResult<SpecVo> listByPPage(SpecQueryDto specQueryDto);

    /**
     * 根据三级分类加载规格属性
     * @param categoryId
     * @return
     */
    CategorySpecAttrVo listByCategoryId(Long categoryId);
}
