package com.likaladi.goods.dto;

import com.likaladi.validate.Range;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author likaladi
 */
@Data
@ApiModel(value = "分组属性")
public class SpuAttrDto {

    @ApiModelProperty(value = "分组名称")
    @NotBlank(message = "组名称不能为空")
    private String group;

    @Valid
    @ApiModelProperty(value = "attr属性")
    @NotEmpty(message = "attr属性列表不能为空")
    private List<AttrsDto> params;

}
