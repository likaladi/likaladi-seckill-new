package com.likaladi.goods.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author likaladi
 */
@Data
@ApiModel(value = "规格属性入参")
public class SpecAttrDto {

    @ApiModelProperty(value = "规格属性id")
    private Long k;

    @ApiModelProperty(value = "规格属性选中值")
    private List<String> v;

}
