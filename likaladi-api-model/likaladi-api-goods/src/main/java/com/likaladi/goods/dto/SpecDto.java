package com.likaladi.goods.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.likaladi.validate.Range;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author likaladi
 */
@Data
@ApiModel(value = "规格属性添加编辑入参")
public class SpecDto {

    private Long id;

    @ApiModelProperty(value = "分组名称")
    @NotBlank(message = "分组名称不能为空")
    private String groupName;

    @ApiModelProperty(value = "属性名称")
    @NotBlank(message = "属性名称不能为空")
    private String name;

    @ApiModelProperty(value = "1-文本框；2-单选框；3-复选框；4-下拉框")
    @Range(value = "1,2,3,4", message = "1-文本框；2-单选框；3-复选框；4-下拉框")
    private Integer type;

    @ApiModelProperty(value = "属性值")
    private List<String> datas;

    @ApiModelProperty(value = "属性单位")
    private String unit;

    @ApiModelProperty(value = "是否全局属性：true-是；false-不是")
    @NotNull(message = "全局属性：true-是；false-不是")
    private Boolean isGloab;

    @ApiModelProperty(value = "是否搜索属性：true-是；false-不是")
    @NotNull(message = "搜索属性：true-是；false-不是")
    private Boolean isSearch;

    @ApiModelProperty(value = "分类id")
    @NotNull(message = "分类id不能为空")
    private Long categoryId;
}
