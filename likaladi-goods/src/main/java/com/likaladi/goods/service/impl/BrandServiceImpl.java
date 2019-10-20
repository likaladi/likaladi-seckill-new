package com.likaladi.goods.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.likaladi.base.BaseServiceImpl;
import com.likaladi.base.PageResult;
import com.likaladi.error.ErrorBuilder;
import com.likaladi.goods.dto.BrandDto;
import com.likaladi.goods.dto.BrandQueryDto;
import com.likaladi.goods.entity.Brand;
import com.likaladi.goods.entity.CategoryBrand;
import com.likaladi.goods.mapper.BrandMapper;
import com.likaladi.goods.service.BrandService;
import com.likaladi.goods.service.CategoryBrandService;
import com.likaladi.goods.vo.BrandVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author liwen
 */
@Service
public class BrandServiceImpl extends BaseServiceImpl<Brand> implements BrandService {

    @Autowired
    private CategoryBrandService categoryBrandService;

    @Autowired
    private BrandMapper brandMapper;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void saveBrand(BrandDto brandDto) {

        Brand brand = new Brand();
        BeanUtils.copyProperties(brandDto, brand);

        this.save(brand);

        if (!CollectionUtils.isEmpty(brandDto.getCategoryIds())) {
            categoryBrandService.saveCategoryBrand(brand.getId(), brandDto.getCategoryIds());
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void updateBrand(BrandDto brandDto) {
        if(Objects.isNull(brandDto.getId())){
            ErrorBuilder.throwMsg("品牌id不能为空");
        }

        Brand brand = new Brand();
        BeanUtils.copyProperties(brandDto, brand);
        this.update(brand);

        if (!CollectionUtils.isEmpty(brandDto.getCategoryIds())) {
            categoryBrandService.saveCategoryBrand(brandDto.getId(), brandDto.getCategoryIds());
        }
    }

    @Override
    public PageResult<BrandVo> listByPPage(BrandQueryDto queryDto) {

        PageHelper.startPage(queryDto.getPage(),queryDto.getRows());

        Page<BrandVo> page = (Page<BrandVo>) brandMapper.selectByPage(queryDto);

        return new PageResult<>(page.getTotal(), page.getResult(), null);
    }

    @Override
    public List<BrandVo> queryBrandByCategory(Long categoryId) {

        List<CategoryBrand> categoryBrands = categoryBrandService.findListBy("categoryId", categoryId);

        List<Long> brandIds = categoryBrands.stream().map(CategoryBrand::getBrandId).collect(Collectors.toList());

        List<Brand> brands = this.findByIds(brandIds);

        List<BrandVo> brandVos = brands.stream().map(brand -> {
            BrandVo brandVo = new BrandVo();
            BeanUtils.copyProperties(brand, brandVo);
            return brandVo;
        }).collect(Collectors.toList());

        return brandVos;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deleteBrandByIds(List<Long> ids) {
        if (CollectionUtils.isEmpty(ids)) {
            ErrorBuilder.throwMsg("id集合不能为空");
        }
        this.deleteByIdList(ids);

        categoryBrandService.deleteByBrandIds(ids);
    }
}
