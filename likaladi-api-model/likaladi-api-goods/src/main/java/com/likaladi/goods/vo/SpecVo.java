package com.likaladi.goods.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.likaladi.goods.dto.SpecDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


@Data
@ApiModel(value = "规格属性返回对象")
public class SpecVo extends SpecDto {

    @JsonIgnore
    private String options;

    @ApiModelProperty(value = "操作类型名称")
    private String typeName;

    @ApiModelProperty(value = "分类名称")
    private String categoryName;
}
