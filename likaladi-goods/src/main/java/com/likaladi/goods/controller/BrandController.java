package com.likaladi.goods.controller;

import com.likaladi.goods.service.BrandService;
import com.likaladi.user.dto.LoginDto;
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
public class BrandController {

    @Autowired
    private BrandService brandService;

    @ApiOperation(value = "用户登录", notes = "用户登录", httpMethod = "POST")
    @PostMapping("/login")
    public void add(@RequestBody @Valid LoginDto loginDto) {

    }


}
