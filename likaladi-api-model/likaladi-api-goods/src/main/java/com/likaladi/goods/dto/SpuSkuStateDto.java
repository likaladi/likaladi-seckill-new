package com.likaladi.goods.dto;

import com.likaladi.validate.Range;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author likaladi
 */
@Data
@ApiModel(value = "spu和sku状态入参")
public class SpuSkuStateDto {

    @ApiModelProperty(value = "spuid或者skuid集合")
    @NotEmpty(message = "id不能为空")
    private List<Long> ids;

    @ApiModelProperty(value = "1-SPU操作；2-SKU操作")
    @NotNull(message = "操作类型operateType不能为空")
    @Range(value = "1,2", message = "1-SPU操作；2-SKU操作")
    private Integer operateType;

    @ApiModelProperty(value = "0-下架；1-上架")
    @NotNull(message = "上下架状态saleable不能为空")
    @Range(value = "0,1", message = "0-下架；1-上架")
    private Integer saleable;
}
