package com.likaladi.goods.controller;

import com.likaladi.base.PageResult;
import com.likaladi.error.ErrorBuilder;
import com.likaladi.goods.dto.SpuDto;
import com.likaladi.goods.dto.SpuQueryDto;
import com.likaladi.goods.service.SpuService;
import com.likaladi.goods.vo.SpuDetailVo;
import com.likaladi.goods.vo.SpuSearchVo;
import com.likaladi.goods.vo.SpuSpecVo;
import com.likaladi.goods.vo.SpuVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
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
    private SpuService spuService;

    @ApiOperation(value = "添加商品", notes = "添加商品")
    @PostMapping
    public void save(@RequestBody @Valid SpuDto spuDto) {
        validate(spuDto, false);
        spuService.saveSpuSku(spuDto);
    }

    @ApiOperation(value = "删除商品", notes = "删除商品")
    @DeleteMapping
    public void deleteByIds(@RequestBody List<Long> ids) {
        spuService.deleteSpuSKu(ids);
    }

    @ApiOperation(value = "根据id查询商品", notes = "根据id查询商品")
    @GetMapping("/{id}")
    public SpuVo queryById(@PathVariable Long id) {
        return spuService.querySpuById(id);
    }


    @ApiOperation(value = "根据id获取Spu详情信息", notes = "根据id获取Spu详情信息")
    @GetMapping("/detail/{id}")
    public SpuDetailVo querySpuSkuDetail(@PathVariable Long id) {
        return spuService.querySpuSkuDetail(id);
    }


    @ApiOperation(value="分页查询规格属性", notes="分页查询规格属性")
    @PostMapping("/listByPage")
    public PageResult<SpuVo> listByPage(@RequestBody @Valid SpuQueryDto spuQueryDto) {
        return spuService.listByPage(spuQueryDto);
    }

    @ApiOperation(value="分页查询上架商品", notes="分页查询上架商品")
    @PostMapping("/upperShelfSpu")
    public PageResult<SpuSearchVo> upperShelfSpu(@RequestBody @Valid SpuQueryDto spuQueryDto) {
        return spuService.upperShelfSpu(spuQueryDto);
    }


    @ApiOperation(value = "编辑商品", notes = "编辑商品")
    @PutMapping
    public void update(@RequestBody @Valid SpuDto spuDto) {
        validate(spuDto, true);
        spuService.updateSpuSku(spuDto);
    }

    private void validate(SpuDto spuDto, Boolean isUpdate){

        if(isUpdate && Objects.isNull(spuDto.getId())){
            ErrorBuilder.throwMsg("商品编辑id不能为空");
        }

    }

}
