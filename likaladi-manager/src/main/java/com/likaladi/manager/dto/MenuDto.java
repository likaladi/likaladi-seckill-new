package com.likaladi.manager.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@ApiModel(value = "菜单增改入参")
public class MenuDto {

    @ApiModelProperty(value = "菜单id")
    private Long id;

    @ApiModelProperty(value = "父菜单id")
    @NotNull(message = "父菜单id不能为空")
    private Long parentId;

    @ApiModelProperty(value = "菜单名")
    @NotBlank(message = "菜单名不能为空")
    private String name;

    @ApiModelProperty(value = "菜单url")
    private String url;

    @ApiModelProperty(value = "菜单样式")
    private String css;

    @ApiModelProperty(value = "排序")
    @Min(value = 1, message = "排序：1-99")
    @Max(value = 99, message = "排序：1-99")
    private Integer sort;
}
