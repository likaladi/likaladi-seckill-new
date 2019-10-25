package com.likaladi.goods.controller;

import com.alibaba.fastjson.JSONObject;
import com.likaladi.base.PageResult;
import com.likaladi.error.ErrorBuilder;
import com.likaladi.goods.dto.SpecDto;
import com.likaladi.goods.dto.SpuDto;
import com.likaladi.goods.dto.SpuQueryDto;
import com.likaladi.goods.entity.Specification;
import com.likaladi.goods.enums.SpecTypEnum;
import com.likaladi.goods.service.SpuService;
import com.likaladi.goods.vo.SpuVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
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
        return spuService.querySpuSkuById(id);
    }

    @ApiOperation(value = "根据id获取Spu详情信息", notes = "根据id获取Spu详情信息")
    @GetMapping("/detail/{id}")
    public SpuVo querySpuSkuDetail(@PathVariable Long id) {
        return spuService.querySpuSkuDetail(id);
    }

    @ApiOperation(value="分页查询规格属性", notes="分页查询规格属性")
    @PostMapping("/listByPage")
    public PageResult<SpuVo> listByPage(@RequestBody @Valid SpuQueryDto spuQueryDto) {
        return spuService.listByPage(spuQueryDto);
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

        /** 校验属性入参 */
        spuDto.getAttrs().forEach(spuAttrDto -> {
            if(!Objects.equals(SpecTypEnum.TEXTBOX.getCode(), spuAttrDto.getType())
                    && CollectionUtils.isEmpty(spuAttrDto.getDatas())){
                ErrorBuilder.throwMsg(String.format("属性【%】：对应的属性值不能为空", spuAttrDto.getName()));
            }
        });

        /** 校验规格入参 */
        spuDto.getSpecs().forEach(spuSpecDto -> {
            if(Objects.isNull(spuSpecDto.getKey())){
                ErrorBuilder.throwMsg("规格名称不能为空");
            }
            if(CollectionUtils.isEmpty(spuSpecDto.getValue())){
                ErrorBuilder.throwMsg(String.format("规格【%】：属性值不能为空", spuSpecDto.getKey()));
            }
            if(Objects.isNull(spuSpecDto.getIsSearch())){
                ErrorBuilder.throwMsg(String.format("规格【%】：搜索属性不能为空", spuSpecDto.getIsSearch()));
            }
        });

        /** 校验SKU规格入参 */
        spuDto.getSkus().forEach(spuSkuDto -> {
            spuSkuDto.getSpecs().forEach(skuSpecDto -> {
                if(Objects.isNull(skuSpecDto.getKey())){
                    ErrorBuilder.throwMsg("SKU规格名称不能为空");
                }
                if(StringUtils.isBlank(skuSpecDto.getValue())){
                    ErrorBuilder.throwMsg(String.format("SKU【%】：规格值不能为空"));
                }
            });
        });
    }

}
