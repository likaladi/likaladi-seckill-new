package com.likaladi.goods.service.impl;

import com.likaladi.base.BaseServiceImpl;
import com.likaladi.error.ErrorBuilder;
import com.likaladi.goods.dto.CategoryDto;
import com.likaladi.goods.entity.Category;
import com.likaladi.goods.mapper.CategoryMapper;
import com.likaladi.goods.service.CategoryService;
import com.likaladi.goods.vo.CategoryVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * @author liwen
 */
@Service
public class CategoryServiceImpl extends BaseServiceImpl<Category> implements CategoryService {

    @Autowired
    private CategoryMapper categoryMapper;

    @Override
    public List<CategoryVo> queryByBrandId(Long brandId) {
        return categoryMapper.queryByBrandId(brandId);
    }

    @Override
    public void save(CategoryDto categoryDto) {

        Category parent = this.findById(categoryDto.getParentId());
        if(Objects.isNull(parent) && categoryDto.getParentId() != 0){
            ErrorBuilder.throwMsg("父节点不存在");
        }

        Category category = new Category();
        BeanUtils.copyProperties(categoryDto, category);
        this.save(category);

        if(categoryDto.getParentId() != 0){
            parent.setIsParent(true);
            this.update(parent);
        }
    }

    @Override
    public List<String> queryNamesByIds(List<Long> ids) {
        List<String> categoryNames = Arrays.asList();

        findByIds(ids).forEach(category -> {
            categoryNames.add(category.getName());
        });

        return categoryNames;
    }
}
