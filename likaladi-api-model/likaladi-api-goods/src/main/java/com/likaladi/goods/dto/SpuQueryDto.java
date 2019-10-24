package com.likaladi.goods.dto;

import com.likaladi.base.BasePage;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @author likaladi
 */
@Data
@ApiModel(value = "商品查询条件")
public class SpuQueryDto extends BasePage {

    @ApiModelProperty(value = "商品标题")
    private String title;

    @ApiModelProperty(value = "0-下架; 1-上架")
    private Integer saleable;

    @ApiModelProperty(value = "销售价格")
    private BigDecimal price;

}
