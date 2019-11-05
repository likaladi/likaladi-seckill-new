package com.likaladi.goods.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.likaladi.goods.dto.SpecDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author likaladi
 */
@Data
@ApiModel(value = "规格属性响应")
public class SpecParamVo extends SpecDto {

    @JsonIgnore
    private String options;

    @ApiModelProperty(value = "操作类型名称")
    private String typeName;

    @ApiModelProperty(value = "分类名称")
    private String categoryName;
}
