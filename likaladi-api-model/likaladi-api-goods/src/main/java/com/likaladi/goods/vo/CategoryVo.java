package com.likaladi.goods.vo;

import com.likaladi.goods.dto.CategoryDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


@Data
@ApiModel(value = "分类返回对象")
public class CategoryVo extends CategoryDto {
}
