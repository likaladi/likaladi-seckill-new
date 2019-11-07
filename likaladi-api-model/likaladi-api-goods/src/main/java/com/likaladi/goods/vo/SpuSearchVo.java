package com.likaladi.goods.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * @author likaladi
 */
@Data
@Builder
@ApiModel(value = "es同步商品对象")
public class SpuSearchVo {

    @ApiModelProperty(value = "商品对象")
    private SpuVo spuVo;

    @ApiModelProperty(value = "商品sku详情对象")
    private SpuDetailVo spuDetailVo;

}
