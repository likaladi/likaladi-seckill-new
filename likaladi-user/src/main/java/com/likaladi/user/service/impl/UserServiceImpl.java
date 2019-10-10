package com.likaladi.user.service.impl;

import com.likaladi.auth.util.AuthUtil;
import com.likaladi.auth.util.CodecUtils;
import com.likaladi.base.BaseServiceImpl;
import com.likaladi.enums.BaseError;
import com.likaladi.error.ErrorBuilder;
import com.likaladi.user.dto.LoginDto;
import com.likaladi.user.entity.RoleUser;
import com.likaladi.user.entity.User;
import com.likaladi.user.model.UserAuth;
import com.likaladi.user.service.RoleUserService;
import com.likaladi.user.service.UserService;
import com.likaladi.user.vo.LoginVo;
import com.likaladi.user.vo.RoleVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl extends BaseServiceImpl<User> implements UserService {

    @Autowired
    private AuthUtil authUtil;

    @Autowired
    private RoleUserService roleUserService;

    @Override
    public LoginVo login(LoginDto loginDto) {
        User user = this.findBy("username", loginDto.getUsername());
        if(Objects.isNull(user)){
            ErrorBuilder.throwMsg(BaseError.USER_LOGIN_FAIL);
        }
//        if(CodecUtils.passwordConfirm(loginDto.getUsername()+loginDto.getPassword(), loginDto.getPassword())){
//            ErrorBuilder.throwMsg(BaseError.USER_LOGIN_FAIL);
//        }

        List<RoleUser> roleUsers = roleUserService.findListBy("userId", user.getId());
        List<RoleVo> roleVos = null;
        if (!CollectionUtils.isEmpty(roleUsers)) {
            roleVos = roleUsers.stream().map(roleUser -> {
                return RoleVo.builder()
                        .roleId(roleUser.getRoleId())
                        .build();
            }).collect(Collectors.toList());
        }

        UserAuth userAuth = UserAuth.builder()
                .userId(user.getId())
                .username(user.getUsername())
                .roles(roleVos)
                .build();

        return authUtil.getLoginResult(userAuth);
    }
}
