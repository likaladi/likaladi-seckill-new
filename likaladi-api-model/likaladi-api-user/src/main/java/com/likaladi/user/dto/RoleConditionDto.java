package com.likaladi.user.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Data
@ApiModel(value = "分页查询角色条件入参")
public class RoleConditionDto implements Serializable {

    @ApiModelProperty(value = "角色code")
    private String code;

    @ApiModelProperty(value = "角色名称")
    private String name;
}
