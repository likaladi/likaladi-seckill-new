package com.likaladi.goods.service;

import com.likaladi.base.BaseService;
import com.likaladi.goods.dto.CategoryDto;
import com.likaladi.goods.entity.Category;
import com.likaladi.goods.vo.CategoryVo;

import java.util.List;
import java.util.Set;

/**
 * @author liwen
 * 处理分类相关逻辑
 */
public interface CategoryService extends BaseService<Category> {

    /**
     * 根据品牌id查询分类列表
     * @param brandId
     * @return
     */
    List<CategoryVo> queryByBrandId(Long brandId);

    List<CategoryVo> queryByIds(Set<Long> ids);

    /**
     * 保存分类
     * @param categoryDto
     */
    void save(CategoryDto categoryDto);

    /**
     * 根据ids列表查询分类对象
     * @param ids
     * @return
     */
    List<CategoryVo> queryCategoryByIds(List<Long> ids);

}
