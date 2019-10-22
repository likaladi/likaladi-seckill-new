package com.likaladi.goods.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


@Data
@ApiModel(value = "商品sku规格")
public class SkuSpecDto {

    @ApiModelProperty(value = "规格")
    private String key;

    @ApiModelProperty(value = "规格值")
    private String value;
}
