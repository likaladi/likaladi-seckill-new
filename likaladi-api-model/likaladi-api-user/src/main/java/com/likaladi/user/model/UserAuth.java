package com.likaladi.user.model;

import com.likaladi.user.vo.RoleVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
@Builder
@ApiModel(value = "登录用户信息")
public class UserAuth implements Serializable {

    @ApiModelProperty(value = "用户id")
    private Long userId;

    @ApiModelProperty(value = "用户名")
    private String username;

    @ApiModelProperty(value = "角色列表")
    private List<RoleVo> roles;
}
