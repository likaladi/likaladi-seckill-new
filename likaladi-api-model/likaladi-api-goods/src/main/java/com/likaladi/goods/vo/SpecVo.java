package com.likaladi.goods.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;
import java.util.List;


@Data
@Builder
@ApiModel(value = "规格属性返回对象")
public class SpecVo {

    @ApiModelProperty(value = "分组名称")
    private String groupName;

    @ApiModelProperty(value = "规格属性参数")
    private List<SpecAttrParamVo> params;
}
