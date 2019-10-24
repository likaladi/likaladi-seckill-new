package com.likaladi.goods.vo;

import com.likaladi.goods.dto.SpuDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

@Data
@ApiModel(value = "商品返回对象")
public class SpuVo extends SpuDto{

    @ApiModelProperty(value = "一级分类名称")
    private String categoryName1;

    @ApiModelProperty(value = "二级分类名称")
    private String categoryName2;

    @ApiModelProperty(value = "三级分类名称")
    private String categoryName3;

    @ApiModelProperty(value = "品牌名称")
    private BrandVo brand;

    @ApiModelProperty(value = "最低销售价")
    private BigDecimal minPrice;

    @ApiModelProperty(value = "最高销售价")
    private BigDecimal maxPrice;


}
