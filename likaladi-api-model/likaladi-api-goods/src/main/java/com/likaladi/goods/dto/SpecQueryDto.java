package com.likaladi.goods.dto;

import com.likaladi.base.BasePage;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author likaladi
 */
@Data
@ApiModel(value = "品牌查询入参")
public class SpecQueryDto extends BasePage {

    @ApiModelProperty(value = "规格名称")
    private String name;

    @ApiModelProperty(value = "三级分类id")
    private Long categoryId;

}
