package com.liwen.demo.user.controller;


import com.alibaba.fastjson.JSONObject;
import com.liwen.demo.user.entity.User;
import com.liwen.demo.user.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/{id}")
    public User queryById(@PathVariable("id") Integer id) {
        User user = null;
        try{
            user = this.userService.queryById(id);
            log.info("=============="+ JSONObject.toJSONString(user));
        }catch(Exception e){
            e.printStackTrace();
        }
        return user;
    }

}
