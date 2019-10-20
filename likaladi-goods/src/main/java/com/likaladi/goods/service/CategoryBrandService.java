package com.likaladi.goods.service;

import com.likaladi.base.BaseService;
import com.likaladi.goods.entity.CategoryBrand;

import java.util.List;

/**
 * @author liwen
 * 处理分类品牌相关逻辑
 */
public interface CategoryBrandService extends BaseService<CategoryBrand> {

    /**
     * 添加品牌id对应多个分类id
     * @param brandId
     * @param categoryIds
     */
    void saveCategoryBrand(Long brandId, List<Long> categoryIds);

    /**
     * 根据品牌id删除品牌分类关系
     * @param brandId
     */
    void deleteByBrandId(Long brandId);

    /**
     * 根据品牌列表id删除对应的品牌分类关系
     * @param brandIds
     */
    void deleteByBrandIds(List<Long> brandIds);
}
