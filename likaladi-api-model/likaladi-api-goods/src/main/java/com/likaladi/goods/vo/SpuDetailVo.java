package com.likaladi.goods.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

import java.util.List;


@Data
@Builder
@ApiModel(value = "商品详情返回对象")
public class SpuDetailVo {

    @ApiModelProperty(value = "商品规格属性")
    private SpuSpecVo spuSpecVo;

    @ApiModelProperty(value = "商品sku列表")
    private List<SpuSkuVo> skus;
}
