package com.likaladi.user.service.impl;

import com.likaladi.auth.util.AuthUtil;
import com.likaladi.auth.util.CodecUtils;
import com.likaladi.base.BaseServiceImpl;
import com.likaladi.enums.BaseError;
import com.likaladi.error.ErrorBuilder;
import com.likaladi.user.dto.LoginDto;
import com.likaladi.user.entity.User;
import com.likaladi.user.model.UserAuth;
import com.likaladi.user.service.UserService;
import com.likaladi.user.vo.LoginVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class UserServiceImpl extends BaseServiceImpl<User> implements UserService {

    @Autowired
    private AuthUtil authUtil;

    @Override
    public LoginVo login(LoginDto loginDto) {
        User user = this.findBy("username", loginDto.getUsername());
        if(Objects.isNull(user)){
            ErrorBuilder.throwMsg(BaseError.USER_LOGIN_FAIL);
        }
//        if(CodecUtils.passwordConfirm(loginDto.getUsername()+loginDto.getPassword(), loginDto.getPassword())){
//            ErrorBuilder.throwMsg(BaseError.USER_LOGIN_FAIL);
//        }

        UserAuth userAuth = UserAuth.builder()
                .userId(user.getId())
                .username(user.getUsername()).build();

        return authUtil.getLoginResult(userAuth);
    }
}
