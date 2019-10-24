package com.likaladi.goods.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.likaladi.goods.dto.SpecDto;
import com.likaladi.goods.dto.SpuSpecDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


@Data
@ApiModel(value = "分类加载规格")
public class CategorySpecVo extends SpuSpecDto {

}
