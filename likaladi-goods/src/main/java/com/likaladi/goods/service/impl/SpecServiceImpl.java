package com.likaladi.goods.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.likaladi.base.BaseServiceImpl;
import com.likaladi.base.PageResult;
import com.likaladi.error.ErrorBuilder;
import com.likaladi.goods.dto.SpecQueryDto;
import com.likaladi.goods.entity.Category;
import com.likaladi.goods.entity.Specification;
import com.likaladi.goods.enums.SpecTypEnum;
import com.likaladi.goods.mapper.SpecificationMapper;
import com.likaladi.goods.service.CategoryService;
import com.likaladi.goods.service.SpecService;
import com.likaladi.goods.vo.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author liwen
 */
@Service
public class SpecServiceImpl extends BaseServiceImpl<Specification> implements SpecService {

    @Autowired
    private SpecificationMapper specificationMapper;

    @Override
    public SpecVo queryById(Long id) {

        SpecVo specVo = specificationMapper.queryById(id);
        if(Objects.isNull(specVo)){
            ErrorBuilder.throwMsg("记录不存在");
        }
        specVo.setDatas(JSONObject.parseArray(specVo.getOptions(), String.class));
        specVo.setTypeName(SpecTypEnum.getValue(specVo.getType()));
        return specVo;
    }

    @Override
    public PageResult<SpecVo> listByPPage(SpecQueryDto specQueryDto) {
        PageHelper.startPage(specQueryDto.getPage(),specQueryDto.getRows());

        Page<SpecVo> page = (Page<SpecVo>) specificationMapper.selectByPage(specQueryDto);

        page.getResult().forEach(specVo -> {
            specVo.setDatas(JSONObject.parseArray(specVo.getOptions(), String.class));
            specVo.setTypeName(SpecTypEnum.getValue(specVo.getType()));
        });

        return new PageResult<>(page.getTotal(), page.getResult(), null);
    }

    @Override
    public CategorySpecAttrVo listByCategoryId(Long categoryId) {
        List<Specification> specifications = this.findListBy("categoryId", categoryId);

        /** 将specifications 过滤出通用属性 转成对应的 categoryAttrVos */
        List<CategoryAttrVo> categoryAttrVos = specifications.stream()
                .filter(specification -> specification.getIsGloab())
                .map(specification -> {
                    CategoryAttrVo categoryAttrVo = new CategoryAttrVo();
                    BeanUtils.copyProperties(specification, categoryAttrVo);
                    categoryAttrVo.setDatas(JSONObject.parseArray(specification.getOptions(), String.class));
                    return categoryAttrVo;
                }).collect(Collectors.toList());

        /** 将specifications 过滤出拓展属性 转成对应的 categorySpecVos */
        List<CategorySpecVo> categorySpecVos = specifications.stream()
                .filter(specification -> !specification.getIsGloab())
                .map(specification -> {
                    CategorySpecVo categorySpecVo = new CategorySpecVo();
                    BeanUtils.copyProperties(specification, categorySpecVo);
                    categorySpecVo.setValue(JSONObject.parseArray(specification.getOptions(), String.class));
                    return categorySpecVo;
                }).collect(Collectors.toList());

        return CategorySpecAttrVo.builder()
                .attrs(categoryAttrVos)
                .specs(categorySpecVos)
                .build();
    }
}
