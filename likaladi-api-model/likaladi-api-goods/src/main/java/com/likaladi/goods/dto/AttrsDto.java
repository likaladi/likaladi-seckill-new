package com.likaladi.goods.dto;

import com.likaladi.validate.Range;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "属性")
public class AttrsDto {

    @ApiModelProperty(value = "属性名称")
    @NotBlank(message = "属性名称不能为空")
    private String name;

    @ApiModelProperty(value = "属性选项值")
    private List<String> datas;

    @ApiModelProperty(value = "1-文本框；2-单选框；3-复选框；4-下拉框")
    @Range(value = "1,2,3,4", message = "1-文本框；2-单选框；3-复选框；4-下拉框")
    private Integer type;

    @ApiModelProperty(value = "属性单位")
    private String unit;

    @ApiModelProperty(value = "是否搜索属性：0不是；1是")
    private Boolean isSearch;

    @ApiModelProperty(value = "选中值")
    @NotEmpty(message = "属性值不能为空")
    private List<String> choiceVal;
}
