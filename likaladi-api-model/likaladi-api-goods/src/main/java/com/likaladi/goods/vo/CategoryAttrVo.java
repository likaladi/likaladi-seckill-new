package com.likaladi.goods.vo;

import com.likaladi.goods.dto.CategoryDto;
import com.likaladi.goods.dto.SpuAttrDto;
import io.swagger.annotations.ApiModel;
import lombok.Data;


@Data
@ApiModel(value = "分类加载属性")
public class CategoryAttrVo extends SpuAttrDto {
}
