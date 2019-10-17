package com.likaladi.upload.controller;

import com.likaladi.user.dto.LoginDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Api(value = "用户接口", description = "用户接口")
@Slf4j
@RestController
@RequestMapping("user")
public class UploadController {

//    @Autowired
//    private BrandService brandService;
//
//    @ApiOperation(value = "用户登录", notes = "用户登录", httpMethod = "POST")
//    @PostMapping("/login")
//    public void add(@RequestBody @Valid LoginDto loginDto) {
//
//    }


}
