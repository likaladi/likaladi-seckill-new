package com.likaladi.goods.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import javax.validation.constraints.NotEmpty;
import java.util.List;

@Data
@ApiModel(value = "规格属性返回对象")
public class SpecVo {

    private Long id;

    @ApiModelProperty(value = "分组名称")
    private String group;

    @ApiModelProperty(value = "属性名称")
    private String name;

    @ApiModelProperty(value = "属性值")
    @NotEmpty(message = "属性值不能为空")
    private List<String> datas;

    @JsonIgnore
    private String options;

    @ApiModelProperty(value = "1-文本框；2-单选框；3-复选框；4-下拉框")
    private Integer type;

    @ApiModelProperty(value = "操作类型名称")
    private String typeName;

    @ApiModelProperty(value = "属性单位")
    private String unit;

    @ApiModelProperty(value = "是否全局属性：0不是；1是")
    private Boolean isGloab;

    @ApiModelProperty(value = "是否搜索属性：0不是；1是")
    private Boolean isSearch;

    @ApiModelProperty(value = "分类id")
    private Long categoryId;

    @ApiModelProperty(value = "分类名称")
    private String categoryName;
}
