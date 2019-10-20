package com.likaladi.goods.controller;

import com.likaladi.base.PageResult;
import com.likaladi.goods.dto.BrandDto;
import com.likaladi.goods.dto.BrandQueryDto;
import com.likaladi.goods.entity.Brand;
import com.likaladi.goods.service.BrandService;
import com.likaladi.goods.service.CategoryBrandService;
import com.likaladi.goods.vo.BrandVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Api(value = "品牌接口", description = "品牌接口")
@Slf4j
@RestController
@RequestMapping("brand")
public class BrandController {

    @Autowired
    private BrandService brandService;

    @Autowired
    private CategoryBrandService categoryBrandService;

    @ApiOperation(value = "添加品牌", notes = "添加品牌")
    @PostMapping
    public void save(@RequestBody @Valid BrandDto brandDto) {
        brandService.saveBrand(brandDto);
    }

    @ApiOperation(value = "根据多个ids删除品牌", notes = "根据多个ids删除品牌")
    @DeleteMapping
    public void deleteByIds(@RequestBody List<Long> ids) {
        brandService.deleteBrandByIds(ids);
    }

    @ApiOperation(value = "根据分类查询品牌", notes = "根据分类查询品牌")
    @GetMapping("cid/{cateogyrId}")
    public List<BrandVo> queryBrandByCategory(@PathVariable Long cateogyrId) {

        return brandService.queryBrandByCategory(cateogyrId);
    }

    @ApiOperation(value = "根据id查询品牌信息", notes = "根据id查询品牌信息")
    @GetMapping("/{id}")
    public BrandVo queryById(@PathVariable Long id) {
        Brand brand =  brandService.findById(id);
        BrandVo brandVo = new BrandVo();
        BeanUtils.copyProperties(brand, brandVo);
        return brandVo;
    }

    @ApiOperation(value="分页查询品牌", notes="分页查询品牌")
    @PostMapping("/listByPage")
    public PageResult<BrandVo> listByPPage(@RequestBody @Valid BrandQueryDto brandQueryDto) {
        return brandService.listByPPage(brandQueryDto);
    }

    @ApiOperation(value = "编辑品牌", notes = "编辑品牌")
    @PutMapping
    public void update(@RequestBody @Valid BrandDto brandDto) {
        brandService.updateBrand(brandDto);
    }


}
