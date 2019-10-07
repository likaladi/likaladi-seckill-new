package com.likaladi.user.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
@ApiModel(value = "登录返回jwt信息")
public class LoginVo implements Serializable {

    @ApiModelProperty(value = "token令牌")
    private String token;

    @ApiModelProperty(value = "过期时间")
    private Long expireTime;
}
