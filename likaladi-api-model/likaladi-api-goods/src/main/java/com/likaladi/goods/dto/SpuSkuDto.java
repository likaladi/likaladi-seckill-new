package com.likaladi.goods.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Data
@ApiModel(value = "商品sku入参")
public class SpuSkuDto {

    @ApiModelProperty(value = "skuId")
    private Long skuId;

    @ApiModelProperty(value = "商品的图片")
    @NotEmpty(message = "商品图片不能为空")
    private List<String> imageList;

    @ApiModelProperty(value = "商品条形码")
    @NotBlank(message = "商品条形码不能为空")
    private String barcode;

    @ApiModelProperty(value = "销售价格")
    @NotNull(message = "销售价格不能为空")
    private BigDecimal price;

    @ApiModelProperty(value = "sku下标组合")
    private List<Integer> indexList;

    @ApiModelProperty(value = "规格属性")
    private Map<String, Object> specs;

    @ApiModelProperty(value = "库存数量")
    @NotNull(message = "库存数量不能为空")
    private Integer stockNum;
}
