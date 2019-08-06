package com.liwen.demo.controller;

import com.liwen.demo.service.RibbonService;
import com.liwen.demo.user.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("ribbon")
public class RibbonTestController {

    @Autowired
    private RibbonService ribbonService;

    /**
     * restTemplate手写模拟客户端负载均衡
     * @param id
     * @return
     */
    @GetMapping
    public User cutomRibbon(@RequestParam("ids") Long id) {
        return ribbonService.queryUserById(id);
    }


    /**
     * restTemplate使用ribbion测试负载均衡
     * @param id
     * @return
     */
    @GetMapping("restTemplate")
    public User testRibbon(@RequestParam("ids") Long id) {
        return ribbonService.queryRestTemplateRibbionUserById(id);
    }

    /**
     * 使用ribbion测试负载均衡
     * @param id
     * @return
     */
    @GetMapping("feign")
    public User testFeign(@RequestParam("ids") Long id) {
        return ribbonService.queryFeignRibbionUserById(id);
    }
}

