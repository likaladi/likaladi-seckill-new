package com.likaladi.goods.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;
import java.util.List;

/**
 * @author likaladi
 */
@Data
@Builder
@ApiModel(value = "规格属性响应对象")
public class SpuSpecVo {

    @ApiModelProperty(value = "属性")
    private List<SpecVo> attrs;

    @ApiModelProperty(value = "规格")
    private List<SpecVo> specs;
}
