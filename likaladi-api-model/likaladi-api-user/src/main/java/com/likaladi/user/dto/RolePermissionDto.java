package com.likaladi.user.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Set;

@Data
@ApiModel(value = "角色分配权限入参")
public class RolePermissionDto implements Serializable {

    @ApiModelProperty(value = "角色ID")
    @NotNull(message = "角色id不能为空")
    private Long roleId;

    @ApiModelProperty(value = "权限ids")
    @NotEmpty(message = "权限ids不能为空")
    private Set<Long> permissionIds;
}
