package com.liwen.demo.service.impl;

import com.liwen.demo.dao.PromoMapper;
import com.liwen.demo.entity.Promo;
import com.liwen.demo.service.PromoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PromoServiceImpl implements PromoService {

    @Autowired
    private PromoMapper promoMapper;

    @Override
    public List<Promo> queryList() {
        return promoMapper.select(null);
    }
}
