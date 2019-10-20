package com.likaladi.goods.dto;

import com.likaladi.base.BasePage;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author likaladi
 */
@Data
@ApiModel(value = "品牌查询入参")
public class BrandQueryDto extends BasePage {

    @ApiModelProperty(value = "品牌名称")
    private String name;

}
