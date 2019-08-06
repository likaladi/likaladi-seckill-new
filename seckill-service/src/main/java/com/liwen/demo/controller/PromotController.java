package com.liwen.demo.controller;

import com.liwen.demo.service.PromoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("promo")
public class PromotController {

    @Autowired
    private PromoService promoService;

    @GetMapping
    public Object queryAll(){
        return promoService.queryList();
    }


}

