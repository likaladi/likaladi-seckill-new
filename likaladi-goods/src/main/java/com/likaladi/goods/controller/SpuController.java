package com.likaladi.goods.controller;

import com.alibaba.fastjson.JSONObject;
import com.likaladi.base.PageResult;
import com.likaladi.error.ErrorBuilder;
import com.likaladi.goods.dto.SpecDto;
import com.likaladi.goods.dto.SpecQueryDto;
import com.likaladi.goods.entity.Specification;
import com.likaladi.goods.service.SpecService;
import com.likaladi.goods.vo.SpecVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Objects;

@Api(value = "商品接口", description = "商品接口")
@Slf4j
@RestController
@RequestMapping("spu")
public class SpuController {

    @Autowired
    private SpecService specService;

    @ApiOperation(value = "添加商品", notes = "添加商品")
    @PostMapping
    public void save(@RequestBody @Valid SpecDto specDto) {
        Specification specification = new Specification();
        BeanUtils.copyProperties(specDto, specification);
        specification.setOptions(JSONObject.toJSONString(specDto.getDatas()));
        specService.save(specification);
    }

    @ApiOperation(value = "删除规格属性", notes = "删除规格属性")
    @DeleteMapping
    public void deleteByIds(@RequestBody List<Long> ids) {
        specService.deleteByIdList(ids);
    }

    @ApiOperation(value = "查询id规格属性", notes = "查询id规格属性")
    @GetMapping("/{id}")
    public SpecVo queryById(@PathVariable Long id) {
        return specService.queryById(id);
    }

    @ApiOperation(value="分页查询规格属性", notes="分页查询规格属性")
    @PostMapping("/listByPage")
    public PageResult<SpecVo> listByPPage(@RequestBody @Valid SpecQueryDto specQueryDto) {
        return specService.listByPPage(specQueryDto);
    }

    @ApiOperation(value = "编辑规格属性", notes = "编辑规格属性")
    @PutMapping
    public void update(@RequestBody @Valid SpecDto specDto) {
        if(Objects.isNull(specDto.getId())){
            ErrorBuilder.throwMsg("id不能为空");
        }
        Specification specification = new Specification();
        BeanUtils.copyProperties(specDto, specification);
        specification.setOptions(JSONObject.toJSONString(specDto.getDatas()));
        specService.update(specification);
    }

}
