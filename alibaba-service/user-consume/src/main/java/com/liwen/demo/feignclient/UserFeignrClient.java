package com.liwen.demo.feignclient;

import com.liwen.demo.user.api.UserApi;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient("user-service")
public interface UserFeignrClient extends UserApi {
}
