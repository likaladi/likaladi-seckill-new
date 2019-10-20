package com.likaladi.goods.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


@Data
@ApiModel(value = "分类返回对象")
public class CategoryVo {

    @ApiModelProperty(value = "类目id")
    private Long id;

    @ApiModelProperty(value = "类目名称")
    private String name;

    @ApiModelProperty(value = "父类目id,顶级类目填0")
    private Long parentId;

    @ApiModelProperty(value = "是否为父节点，0为否，1为是")
    private Boolean isParent;

    @ApiModelProperty(value = "排序指数，越小越靠前")
    private Integer sort;
}
