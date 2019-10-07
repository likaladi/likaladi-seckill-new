package com.likaladi.auth.interceptor;

import com.likaladi.auth.util.AuthUtil;
import com.likaladi.enums.BaseError;
import com.likaladi.error.ErrorBuilder;
import io.micrometer.core.instrument.util.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * @author likaladi
 * token拦截校验
 */
@Slf4j
@Component
public class LoginInterceptor implements HandlerInterceptor {

    @Autowired
    private AuthUtil authUtil;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        String token = request.getHeader(AuthUtil.AUTH_TOKEN);
        log.info("请求地址 = {}，校验TOKEN = {}", request.getRequestURI(), token);

        if (StringUtils.isBlank(token)) {
            ErrorBuilder.throwMsg(BaseError.TOKEN_NOT_NULL);
        }

        return authUtil.checkJWT(token, request);
    }

}

