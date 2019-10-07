package com.likaladi.user.service;

import com.likaladi.base.BaseService;
import com.likaladi.user.dto.LoginDto;
import com.likaladi.user.entity.User;
import com.likaladi.user.vo.LoginVo;

/**
 * @author liwen
 * 处理用户相关逻辑
 */
public interface UserService extends BaseService<User> {
    /**
     * 用户登录
     * @param loginDto
     * @return
     */
    LoginVo login(LoginDto loginDto);
}
