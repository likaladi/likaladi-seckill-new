package com.likaladi.auth.config;

import com.likaladi.auth.interceptor.LoginInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

/**
 * @author likaladi
 * 拦截器配置
 */
@Configuration
public class IntercepterConfig extends WebMvcConfigurationSupport {

    @Bean
    LoginInterceptor localInterceptor() {
        return new LoginInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        registry.addInterceptor(localInterceptor())
                .addPathPatterns("/**")
                //放开接口，不进行拦截
                .excludePathPatterns("/user/login")
                .excludePathPatterns("/swagger-resources/**", "/webjars/**", "/v2/**", "/swagger-ui.html/**");
    }

    @Override
    protected void addResourceHandlers(ResourceHandlerRegistry registry) {
        //不拦截swagger
        registry.addResourceHandler("swagger-ui.html")
                .addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");
    }

}