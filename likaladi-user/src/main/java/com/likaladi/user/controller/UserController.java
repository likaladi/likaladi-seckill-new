package com.likaladi.user.controller;

import com.likaladi.user.dto.LoginDto;
import com.likaladi.user.service.UserService;
import com.likaladi.user.model.UserAuth;
import com.likaladi.user.vo.LoginVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Api(value = "用户接口", description = "用户接口")
@Slf4j
@RestController
@RequestMapping("user")
public class UserController {

    @Autowired
    private UserService userService;

    @ApiOperation(value = "用户登录", notes = "用户登录", httpMethod = "POST")
    @PostMapping("/login")
    public LoginVo login(@RequestBody @Valid LoginDto loginDto) {
        return userService.login(loginDto);
    }

}
