package com.likaladi.goods.controller;

import com.likaladi.error.ErrorBuilder;
import com.likaladi.goods.dto.CategoryDto;
import com.likaladi.goods.entity.Category;
import com.likaladi.goods.service.CategoryService;
import com.likaladi.goods.vo.CategoryVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Api(value = "分类接口", description = "分类接口")
@Slf4j
@RestController
@RequestMapping("category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @ApiOperation(value = "添加分类", notes = "添加分类")
    @PostMapping
    public void save(@RequestBody @Valid CategoryDto categoryDto) {
        categoryService.save(categoryDto);
    }

    @ApiOperation(value = "根据当前分类id查询子集列表", notes = "根据当前分类id查询子集列表")
    @GetMapping("/childs/{id}")
    public List<CategoryVo> childs (@PathVariable Long id) {
        List<Category> categories = categoryService.findListBy("parentId", id);
        return categories.stream().map(category -> {
            CategoryVo categoryVo = new CategoryVo();
            BeanUtils.copyProperties(category, categoryVo);
            return categoryVo;
        }).collect(Collectors.toList());
    }

    @ApiOperation(value = "根据多个ids删除分类", notes = "根据多个ids删除分类")
    @DeleteMapping
    public void save(@RequestBody List<Long> ids) {
        if (CollectionUtils.isEmpty(ids)) {
            ErrorBuilder.throwMsg("id集合不能为空");
        }
        categoryService.deleteByIdList(ids);
    }

    @ApiOperation(value = "根据id查询分类信息", notes = "根据id查询分类信息")
    @GetMapping("/{id}")
    public CategoryVo save(@PathVariable Long id) {
        Category category =  categoryService.findById(id);
        CategoryVo categoryVo = new CategoryVo();
        BeanUtils.copyProperties(category, categoryVo);
        return categoryVo;
    }

    @ApiOperation(value = "通过品牌id查询商品分类", notes = "通过品牌id查询商品分类")
    @GetMapping("bid/{brandId}")
    public List<CategoryVo> queryByBrandId(@PathVariable Long brandId) {
        return categoryService.queryByBrandId(brandId);
    }

    @ApiOperation(value = "编辑分类", notes = "编辑分类")
    @PutMapping
    public void update(@RequestBody @Valid CategoryDto categoryDto) {
        if(Objects.isNull(categoryDto.getId())){
            ErrorBuilder.throwMsg("分类id不能为空");
        }
        Category category = new Category();
        BeanUtils.copyProperties(categoryDto, category);
        categoryService.update(category);
    }

    @ApiOperation(value = "根据多个ids查询分类名称", notes = "根据多个ids查询分类名称")
    @PostMapping("ids")
    public List<CategoryVo> queryNamesByIds(@RequestBody List<Long> ids) {
        return categoryService.queryCategoryByIds(ids);
    }


}
