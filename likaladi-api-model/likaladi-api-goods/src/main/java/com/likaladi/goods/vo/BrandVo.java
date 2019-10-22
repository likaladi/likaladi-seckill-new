package com.likaladi.goods.vo;

import com.likaladi.goods.dto.BrandDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
@ApiModel(value = "品牌返回对象")
public class BrandVo extends BrandDto {
}
