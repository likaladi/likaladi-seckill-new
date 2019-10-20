package com.likaladi.goods.service.impl;

import com.likaladi.base.BaseServiceImpl;
import com.likaladi.goods.entity.CategoryBrand;
import com.likaladi.goods.mapper.CategoryBrandMapper;
import com.likaladi.goods.service.CategoryBrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author liwen
 */
@Service
public class CategoryBrandServiceImpl extends BaseServiceImpl<CategoryBrand> implements CategoryBrandService {

    @Autowired
    private CategoryBrandMapper categoryBrandMapper;

    @Override
    public void saveCategoryBrand(Long brandId, List<Long> categoryIds) {

        deleteByBrandId(brandId);

        List<CategoryBrand> categoryBrands = categoryIds.stream().map(categoryId -> {
            return CategoryBrand.builder()
                    .brandId(brandId)
                    .categoryId(categoryId)
                    .build();
        }).collect(Collectors.toList());

        this.save(categoryBrands);
    }

    @Override
    public void deleteByBrandId(Long brandId) {
        categoryBrandMapper.deleteByBrandId(brandId);
    }

    @Override
    public void deleteByBrandIds(List<Long> brandIds) {
        categoryBrandMapper.deleteByBrandIds(brandIds);
    }
}
