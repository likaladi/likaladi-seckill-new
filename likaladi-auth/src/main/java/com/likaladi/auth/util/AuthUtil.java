package com.likaladi.auth.util;

import com.alibaba.fastjson.JSONObject;
import com.likaladi.enums.BaseError;
import com.likaladi.error.ErrorBuilder;
import com.likaladi.user.model.UserAuth;
import com.likaladi.user.vo.LoginVo;
import com.likaladi.user.vo.RoleVo;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author liwen
 * toke验证/登陆信息的工具类
 */
@Slf4j
@Component
public class AuthUtil {

    @Autowired
    private JwtOperatorUtil jwtOperatorUtil;

    public static final String AUTH_USER = "AUTH_USER";

    public static final String AUTH_TOKEN = "X-Auth-Token";


    public static UserAuth getLoginUserInfo() {
        HttpServletRequest request = getRequest();
        UserAuth userAuthResp = (UserAuth) request.getAttribute(AUTH_USER);
        return userAuthResp;
    }

    public Long getLoginUserId() {
        return getLoginUserInfo().getUserId();
    }

    public static HttpServletRequest getRequest() {
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        ServletRequestAttributes sra = (ServletRequestAttributes) requestAttributes;
        RequestContextHolder.setRequestAttributes(sra, true);
        HttpServletRequest request = sra.getRequest();
        return request;
    }

    /**
     * 创建t绑定用户登录信息的TOKEN
     *
     * @param userAuth
     * @return
     */
    public String createToken(UserAuth userAuth) {

        Map<String, Object> userInfoMap = new HashMap<>(3);
        userInfoMap.put("userId", userAuth.getUserId());
        userInfoMap.put("username", userAuth.getUsername());
        userInfoMap.put("roles", JSONObject.toJSONString(userAuth.getRoles()));

        //创建token
        String token = jwtOperatorUtil.generateToken(userInfoMap);

        log.info("用户{}登录成功，生成的token = {}, 有效期到：{}",
                userAuth.getUsername(), token, jwtOperatorUtil.getExpirationTime());
        return token;
    }

    /**
     * 是否成功解析TOKEN
     *
     * @param token
     * @param request
     * @return true表示解析成功；false表示解析失败
     */
    public boolean checkJWT(String token, HttpServletRequest request) {
        boolean isParseSuccess = false;
        try {
            Claims claims = jwtOperatorUtil.getClaimsFromToken(token);

            UserAuth userAuthResp = UserAuth.builder()
                    .userId(Long.parseLong(String.valueOf(claims.get("userId"))))
                    .username(String.valueOf(claims.get("username")))
                    .roles(JSONObject.parseArray((String.valueOf(claims.get("roles"))),RoleVo.class)).build();

            request.setAttribute(AuthUtil.AUTH_USER, userAuthResp);

            isParseSuccess = true;
        } catch (Exception e) {
            log.error("解析token失败：{}", e);
            ErrorBuilder.throwMsg(BaseError.TOKEN_INVALID);
        }
        return isParseSuccess;
    }

    /**
     * 登录成功响应信息
     *
     * @param userAuth
     * @return
     */
    public LoginVo getLoginResult(UserAuth userAuth) {
        //创建token
        String token = createToken(userAuth);
        return LoginVo.builder()
                .token(token)
                .expireTime(jwtOperatorUtil.getExpirationTime().getTime())
                .build();
    }
}
