package com.likaladi.goods.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.likaladi.base.BaseServiceImpl;
import com.likaladi.goods.dto.SpuDto;
import com.likaladi.goods.entity.Spu;
import com.likaladi.goods.entity.SpuDetail;
import com.likaladi.goods.service.SpuDetailService;
import com.likaladi.goods.service.SpuService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.Arrays;

/**
 * @author liwen
 */
@Service
public class SpuDetailServiceImpl extends BaseServiceImpl<SpuDetail> implements SpuDetailService {

}
