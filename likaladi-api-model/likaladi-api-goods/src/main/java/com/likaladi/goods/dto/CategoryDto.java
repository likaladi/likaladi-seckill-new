package com.likaladi.goods.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author likaladi
 */
@Data
@ApiModel(value = "分类添加编辑入参")
public class CategoryDto {

    @ApiModelProperty(value = "类目id")
    private Long id;

    @ApiModelProperty(value = "类目名称")
    @NotBlank(message = "类目名称不能为空")
    private String name;

    @ApiModelProperty(value = "父类目id,顶级类目填0")
    @NotNull(message = "父节点id不能为空")
    private Long parentId;

    @ApiModelProperty(value = "是否为父节点，0为否，1为是")
    private Boolean isParent;

    @ApiModelProperty(value = "排序指数，越小越靠前")
    private Integer sort;
}
