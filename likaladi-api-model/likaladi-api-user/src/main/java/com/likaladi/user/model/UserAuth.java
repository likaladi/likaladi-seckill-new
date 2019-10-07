package com.likaladi.user.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
@ApiModel(value = "登录用户信息")
public class UserAuth implements Serializable {

    @ApiModelProperty(value = "用户id")
    private Long userId;

    @ApiModelProperty(value = "用户名")
    private String username;
}
