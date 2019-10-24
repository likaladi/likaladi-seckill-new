package com.likaladi.goods.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

import java.util.List;


@Data
@Builder
@ApiModel(value = "分类加载规格属性")
public class CategorySpecAttrVo  {

    @ApiModelProperty(value = "属性")
    private List<CategoryAttrVo> attrs;

    @ApiModelProperty(value = "规格")
    private List<CategorySpecVo> specs;
}
