package com.likaladi.goods.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Component
public class RestTemplateUtil {

    @Autowired
    private RestTemplate restTemplate;

    public void test(){

        //设置请求头
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        //  封装参数，千万不要替换为Map与HashMap，否则参数无法传递
        MultiValueMap<String, String> params= new LinkedMultiValueMap<>();
        //添加请求的参数
        params.add("hello", "hello");
        params.add("world", "world");
        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(params, headers);
        //  执行HTTP请求
        //最后的参数需要用String.class  使用其他的会报错
        ResponseEntity<String> response = restTemplate.exchange("此处为url地址", HttpMethod.POST, requestEntity, String.class);
        String body = response.getBody();
        if(body != null){
            JSONObject data = (JSONObject) JSON.parse(body);
            //data就是返回的结果
        }else{

        }
    }
}
