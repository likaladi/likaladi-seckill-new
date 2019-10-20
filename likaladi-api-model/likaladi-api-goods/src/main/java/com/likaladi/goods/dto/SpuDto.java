package com.likaladi.goods.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;


@Data
@ApiModel(value = "商品添加编辑入参")
public class SpuDto {

    private Long id;

    @ApiModelProperty(value = "商家id")
    @NotNull(message = "商家id不能为空")
    private Long sellerId;

    @ApiModelProperty(value = "标题")
    @NotBlank(message = "标题不能为空")
    private String title;

    @ApiModelProperty(value = "子标题")
    private String subTitle;

    @ApiModelProperty(value = "主图")
    @NotBlank(message = "主图不能为空")
    private String image;

    @ApiModelProperty(value = "1级类目id")
    @NotNull(message = "1级类目id不能为空")
    private Long cid1;

    @ApiModelProperty(value = "2级类目id")
    @NotNull(message = "2级类目id不能为空")
    private Long cid2;

    @ApiModelProperty(value = "3级类目id")
    @NotNull(message = "3级类目id不能为空")
    private Long cid3;

    @ApiModelProperty(value = "品牌id")
    @NotNull(message = "品牌id不能为空")
    private Long brandId;

    @ApiModelProperty(value = "商品描述")
    private String description;

    @Valid
    @ApiModelProperty(value = "规格属性")
    @NotEmpty(message = "规格属性不能为空")
    private List<SpuSpecDto> specs;



}
