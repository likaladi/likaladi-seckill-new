package com.likaladi.goods.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import java.util.List;

@Data
@ApiModel(value = "品牌添加编辑入参")
public class BrandDto {

    private Long id;

    @ApiModelProperty(value = "品牌名称")
    @NotBlank(message = "品牌名称不能为空")
    private String name;

    @ApiModelProperty(value = "品牌图片地址")
    private String image;

    @ApiModelProperty(value = "品牌的首字母")
    @Length(max = 1, message = "首字母只能是一个字符")
    private String firstChar;

    @ApiModelProperty(value = "分类id列表")
    private List<Long> categoryIds;
}
