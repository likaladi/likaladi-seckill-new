package com.likaladi.goods.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author likaladi
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "规格属性响应")
public class SpecAttrParamVo {

    @ApiModelProperty(value = "规格属性id")
    private String k;

    @ApiModelProperty(value = "规格属性选中值")
    private List<String> v;

    @ApiModelProperty(value = "规格属性名称")
    private String name;

    @ApiModelProperty(value = "规格属性值")
    private List<String> options;

    @ApiModelProperty(value = "操作类型：1文本框；2单选框；3复选框；4下拉框")
    private Integer type;

    @ApiModelProperty(value = "是否搜索属性")
    private Boolean isSearch;

}
