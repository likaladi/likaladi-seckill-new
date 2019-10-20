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
import com.likaladi.goods.vo.BrandVo;
import com.likaladi.goods.vo.SpecVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

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
}
