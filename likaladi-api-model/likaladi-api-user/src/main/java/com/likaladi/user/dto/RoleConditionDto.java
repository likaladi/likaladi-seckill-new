package com.likaladi.user.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Data
@ApiModel(value = "分页查询角色条件入参")
public class RoleConditionDto implements Serializable {

    @ApiModelProperty(value = "角色code")
    private String code;

    @ApiModelProperty(value = "角色名称")
    private String name;

    @ApiModelProperty(value = "当前页")
    @Min(value = 1, message = "页数：1-100")
    @Max(value = 100, message = "页数：1-100")
    private Integer page = 1;

    @ApiModelProperty(value = "每页显示行数")
    @Min(value = 1, message = "显示行数：1-100")
    @Max(value = 100, message = "显示行数：1-100")
    private Integer rows = 20;
}
