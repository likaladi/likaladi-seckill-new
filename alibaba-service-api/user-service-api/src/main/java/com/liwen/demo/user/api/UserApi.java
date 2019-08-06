package com.liwen.demo.user.api;

import com.liwen.demo.user.entity.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;


@RequestMapping("user")
public interface UserApi {

    @GetMapping("/{id}")
    User queryById(@PathVariable("id") Integer id);
}
