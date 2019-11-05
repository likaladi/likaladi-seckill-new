package com.likaladi.goods.controller;

import com.alibaba.fastjson.JSONObject;
import com.likaladi.base.PageResult;
import com.likaladi.error.ErrorBuilder;
import com.likaladi.goods.dto.SpecDto;
import com.likaladi.goods.dto.SpecQueryDto;
import com.likaladi.goods.entity.Specification;
import com.likaladi.goods.service.SpecService;
import com.likaladi.goods.service.SpuService;
import com.likaladi.goods.vo.SpecParamVo;
import com.likaladi.goods.vo.SpuSpecVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Api(value = "规格属性接口", description = "规格属性接口")
@Slf4j
@RestController
@RequestMapping("spec")
public class SpecController {

    @Autowired
    private SpecService specService;

    @Autowired
    private SpuService spuService;

    @ApiOperation(value = "添加规格属性", notes = "添加规格属性")
    @PostMapping
    public void save(@RequestBody @Valid SpecDto specParamDto) {

        Specification specification = new Specification();
        BeanUtils.copyProperties(specParamDto, specification);
        specification.setOptions(JSONObject.toJSONString(specParamDto.getDatas()));

        specService.save(specification);

    }

    @ApiOperation(value = "删除规格属性", notes = "删除规格属性")
    @DeleteMapping
    public void deleteByIds(@RequestBody List<Long> ids) {

        List<Specification> specifications = specService.findByIds(ids);

        /** 筛选规格 转为 对应的分类id集合 */
        List<Long> categoryIds = specifications.stream()
                .filter(specification -> !specification.getIsGloab())
                .map(Specification::getCategoryId)
                .collect(Collectors.toList());

        int count = spuService.queryCountByCateogryIds(categoryIds);

        if(count > 0){
            ErrorBuilder.throwMsg("已绑定商品的规格，不能删除");
        }

        specService.deleteByIdList(ids);
    }

    @ApiOperation(value = "查询id规格属性", notes = "查询id规格属性")
    @GetMapping("/{id}")
    public SpecParamVo queryById(@PathVariable Long id) {
        return specService.queryById(id);
    }

    @ApiOperation(value = "根据三级分类id获取规格属性", notes = "根据三级分类id获取规格属性")
    @GetMapping("/listBy/{cid}")
    public SpuSpecVo listByCategoryId(@PathVariable Long cid) {
        return specService.listByCategoryId(cid, null);
    }

    @ApiOperation(value="分页查询规格属性", notes="分页查询规格属性")
    @PostMapping("/listByPage")
    public PageResult<SpecParamVo> listByPPage(@RequestBody @Valid SpecQueryDto specQueryDto) {
        return specService.listByPage(specQueryDto);
    }

    @ApiOperation(value = "编辑规格属性", notes = "编辑规格属性")
    @PutMapping
    public void update(@RequestBody @Valid SpecDto specParamDto) {
        if(Objects.isNull(specParamDto.getId())){
            ErrorBuilder.throwMsg("id不能为空");
        }

        Specification specification = new Specification();
        BeanUtils.copyProperties(specParamDto, specification);
        specification.setOptions(JSONObject.toJSONString(specParamDto.getDatas()));
        specService.update(specification);

    }

}
