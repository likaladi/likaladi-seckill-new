package com.likaladi.goods.service;

import com.likaladi.base.BaseService;
import com.likaladi.base.PageResult;
import com.likaladi.goods.dto.BrandDto;
import com.likaladi.goods.dto.BrandQueryDto;
import com.likaladi.goods.entity.Brand;
import com.likaladi.goods.vo.BrandVo;

import java.util.List;

/**
 * @author liwen
 * 处理品牌相关逻辑
 */
public interface BrandService extends BaseService<Brand> {

    /**
     * 保存品牌
     * @param brandDto
     */
    void saveBrand(BrandDto brandDto);

    /**
     * 更新品牌
     * @param brandDto
     */
    void updateBrand(BrandDto brandDto);

    /**
     * 分页查询品牌
     * @param queryDto
     * @return
     */
    PageResult<BrandVo> listByPPage(BrandQueryDto queryDto);

    /**
     * 根据分类id查询品牌列表
     * @param categoryId
     * @return
     */
    List<BrandVo> queryBrandByCategory(Long categoryId);

    /**
     * 根据多个ids删除品牌
     * @param ids
     */
    void deleteBrandByIds(List<Long> ids);

}
