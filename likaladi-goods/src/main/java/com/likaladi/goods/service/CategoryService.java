package com.likaladi.goods.service;

import com.likaladi.base.BaseService;
import com.likaladi.goods.dto.CategoryDto;
import com.likaladi.goods.entity.Category;
import com.likaladi.goods.vo.CategoryVo;

import java.util.List;

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

    /**
     * 保存分类
     * @param categoryDto
     */
    void save(CategoryDto categoryDto);

}
