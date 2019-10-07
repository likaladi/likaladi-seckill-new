package com.likaladi.response;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.Objects;

/**
 * @author likaladi
 * controller响应成功
 * 统一返回Rest风格的数据
 */
@Slf4j
//@ControllerAdvice(basePackages = "com.likaladi.*.controller")
@ControllerAdvice(annotations = RestController.class)
public class RestResultWrapper implements ResponseBodyAdvice<Object> {

    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        return true;
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType,
                                  Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {

        if(Objects.isNull(body)){
            log.info("ResponseBodyAdvice body is null");
            return Response.ok();
        }

        if(body instanceof ResponseResult){
            log.info("ResponseBodyAdvice body is ResponseResult");
            return body;
        }
        if(body instanceof String){
            log.info("ResponseBodyAdvice body is String");
            writeJsonString(response, Response.okString(String.valueOf(body)));
            return null;
        }

        log.info("ResponseBodyAdvice body is {}", body.toString());
        return Response.ok(body);
    }

    private void writeJsonString(ServerHttpResponse response, ResponseResult<String> result){
        try {
            ServletServerHttpResponse servletServerResponse = (ServletServerHttpResponse)response;
            HttpServletResponse rsp = servletServerResponse.getServletResponse();
            // 解决json中文乱码
            rsp.setContentType("text/json;charset=UTF-8");
            rsp.setCharacterEncoding("UTF-8");
            PrintWriter out = rsp.getWriter();
            out.println(JSONObject.toJSONString(result));
            out.flush();
            out.close();
        } catch (Exception e) {
            log.error("浏览器输出json字符串异常：", e);
        }
    }
}